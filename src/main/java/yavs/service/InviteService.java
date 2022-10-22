package yavs.service;

import org.springframework.stereotype.Service;
import yavs.model.invite.Invite;
import yavs.repository.invite.InviteRepository;

import java.util.UUID;

@Service
public class InviteService implements IBasedService<Invite, UUID> {
    private final InviteRepository repo;

    public InviteService(InviteRepository repo) {
        this.repo = repo;
    }

    @Override
    public Invite save(Invite obj) {
        return null;
    }

    @Override
    public Invite update(Invite obj) {
        return null;
    }

    @Override
    public Invite getById(UUID id) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
