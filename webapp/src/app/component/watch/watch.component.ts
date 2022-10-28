import { ChangeDetectionStrategy, Component, Inject, OnInit, TemplateRef, ViewChild, ViewEncapsulation } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { TuiDialogContext } from '@taiga-ui/core';
import { TuiPreviewComponent, TuiPreviewDialogService } from '@taiga-ui/addon-preview';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';

import { Guid } from 'guid-typescript';

declare var SockJS: any
declare var Stomp: any
declare var YT: any

@Component({
  selector: 'app-watch',
  templateUrl: './watch.component.html',
  styleUrls: ['./watch.component.less'],
  encapsulation: ViewEncapsulation.None,
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class WatchComponent implements OnInit {
  url: String = ''
  ytId: SafeUrl = ''
  player: any
  lastData: number = -2
  lastTime: number = -1
  id = Guid.create()
  pc: any

  public stompClient: any;
  initializeWebSocketConnection() {
    const serverUrl = 'http://localhost:8080/ws'
    const ws = new SockJS(serverUrl)
    this.stompClient = Stomp.over(ws)
    const that = this
    this.stompClient.connect({}, (frame: any) => {
      that.stompClient.subscribe('/out/default/', (message: any) => {
        that.handle(message)
      })
    })
  }

  handle(message: any) {
    let msg = JSON.parse(message.body)
    if (msg.state != null) {
      console.log(this.id.toString() != msg.from)
      if (this.id.toString() != msg.from) {
        this.lastData = msg.state
        if (Math.abs(msg.time - this.player.playerInfo.currentTime) >= 0.5) {
          this.lastTime = msg.time
          this.player.seekTo(msg.time, true)
        }
        if (msg.state == 1) {
          this.player.playVideo()
        }
        if (msg.state == 2) {
          this.player.pauseVideo()
        }
        if (msg.state == 0) {
          this.pc.complete()
        }
      }
      return
    }
    let id = msg.text
    /*this.ytId = this.
      sanitizer.
      bypassSecurityTrustResourceUrl(
        "https://www.youtube.com/embed/" + 
        id + 
        "?autoplay=0?enablejsapi=1"
      );
    console.log(this.ytId)*/
    this.ytId = id
    this.show()
  }

  sendMessage(state: number | null = null, time: number | null = null) {
    this.stompClient.send('/in/', {}, JSON.stringify({ 'from': this.id.toString(), 'text': state ? null : this.url, 'state' : state, 'time': time }))
  }

  @ViewChild(`preview`)
  readonly preview?: TemplateRef<TuiDialogContext<void>>;

  constructor(private sanitizer: DomSanitizer, @Inject(TuiPreviewDialogService) private readonly previewDialogService: TuiPreviewDialogService) {
    this.initializeWebSocketConnection()
  }

  show(): void {
    this.previewDialogService.open(this.preview || ``).subscribe()
  }

  ready(pc: any) {
    this.pc = pc
    new YT.Player('player', {
      videoId: this.ytId,
      events: {
        'onReady': (event: any) => {
          this.player = event.target
        },
        'onStateChange': (event: any) => {
          if (event.data == 1 || event.data == 2) {
            if (event.data != this.lastData || Math.abs(event.target.playerInfo.currentTime - this.lastTime) > 0.5) {
              this.sendMessage(event.data, event.target.playerInfo.currentTime)
            }
          }
          if (event.data == 0) {
            this.pc.complete()
          }
        }
      }
    })
  }

  close(): void {
    this.pc.complete()
    this.sendMessage(0)
  }

  ngOnInit(): void {
  }

  onSubmit(): void {
    this.sendMessage()
  }

  readonly watchForm = new FormGroup({
    urlControl: new FormControl('', [
    ]),
  })
}
