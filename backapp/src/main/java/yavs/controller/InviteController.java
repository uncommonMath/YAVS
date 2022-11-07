package yavs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yavs.model.invite.Invite;
import yavs.model.lobby.Lobby;
import yavs.service.InviteService;
import yavs.service.LobbyService;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/invite")
public class InviteController {
    private final InviteService inviteService;
    private final LobbyService lobbyService;

    public InviteController(InviteService service, LobbyService lobbyService) {
        this.inviteService = service;
        this.lobbyService = lobbyService;
    }

    @GetMapping
    @RolesAllowed("ROLE_USER")
    public ResponseEntity<Invite> gitInviteByLobby(@RequestParam("lobbyId") Long id) {
        var lobby = lobbyService.getById(id);
        if (lobby != null) {
            return new ResponseEntity<>(inviteService.getByLobby(lobby), HttpStatus.OK);
        } else {
            throw new EntityNotFoundException("Lobby with id=" + id + " not found!");
        }
    }

    @PostMapping
    public ResponseEntity<Invite> revoke(@RequestBody Lobby lobby) {
        if (lobbyService.getById(lobby.getId()) != null) {
            return new ResponseEntity<>(inviteService.revoke(lobby), HttpStatus.OK);
        } else {
            throw new EntityNotFoundException("Lobby with id=" + lobby.getId() + " not found!");
        }
    }
}