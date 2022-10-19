package yavs.service;

import org.springframework.stereotype.Service;
import yavs.repository.invite.InviteRepository;

@Service
public class InviteService {
    private final InviteRepository repo;

    public InviteService(InviteRepository repo) {
        this.repo = repo;
    }
}
