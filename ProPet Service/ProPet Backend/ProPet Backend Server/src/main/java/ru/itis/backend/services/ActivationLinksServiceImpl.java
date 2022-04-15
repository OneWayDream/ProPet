package ru.itis.backend.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.backend.dto.ActivationLinkDto;
import ru.itis.backend.dto.UserDto;
import ru.itis.backend.exceptions.EntityNotExistsException;
import ru.itis.backend.exceptions.EntityNotFoundException;
import ru.itis.backend.exceptions.LinkNotExistsException;
import ru.itis.backend.models.ActivationLink;
import ru.itis.backend.models.User;
import ru.itis.backend.repositories.ActivationLinkRepository;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivationLinksServiceImpl implements ActivationLinksService {

    @NonNull
    protected ActivationLinkRepository activationLinkRepository;

    @Override
    public List<ActivationLinkDto> findAll() {
        return ActivationLinkDto.from(activationLinkRepository.findAll().stream()
                .filter(link -> !link.getIsDeleted())
                .collect(Collectors.toList()));
    }

    @Override
    public void delete(ActivationLinkDto activationLinkDto) {
        try{
            ActivationLink activationLinkForDeletion = activationLinkRepository.findById(activationLinkDto.getId())
                    .filter(link -> link.getIsDeleted()==null)
                    .orElseThrow(EntityNotExistsException::new);
            activationLinkForDeletion.setIsDeleted(true);
            activationLinkRepository.save(activationLinkForDeletion);
        } catch (Exception ex){
            throw new PersistenceException(ex);
        }
    }

    @Override
    public ActivationLinkDto add(ActivationLinkDto activationLinkDto) {
        ActivationLink newActivationLink = ActivationLinkDto.to(activationLinkDto);
        activationLinkRepository.save(newActivationLink);
        return ActivationLinkDto.from(newActivationLink);
    }

    @Override
    public ActivationLinkDto findById(Long aLong) {
        return ActivationLinkDto.from(activationLinkRepository.findById(aLong)
                .filter(link -> !link.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public ActivationLinkDto update(ActivationLinkDto activationLinkDto) {
        ActivationLink updatedActivationLink = activationLinkRepository.save(ActivationLinkDto.to(activationLinkDto));
        return ActivationLinkDto.from(updatedActivationLink);
    }

    @Override
    public ActivationLinkDto findByLinkValue(String linkValue) {
        return ActivationLinkDto.from(activationLinkRepository.findByLinkValue(linkValue)
                .filter(activationLink -> !activationLink.getIsDeleted())
                .orElseThrow(LinkNotExistsException::new));
    }
}
