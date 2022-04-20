package ru.itis.backend.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.backend.dto.PetInfoDto;
import ru.itis.backend.dto.UserDto;
import ru.itis.backend.exceptions.EntityNotExistsException;
import ru.itis.backend.exceptions.EntityNotFoundException;
import ru.itis.backend.models.PetInfo;
import ru.itis.backend.models.User;
import ru.itis.backend.repositories.PetInfoRepository;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetInfoServiceImpl implements PetInfoService {

    @NonNull
    protected PetInfoRepository petInfoRepository;
    @NonNull
    protected UsersService usersService;

    @Override
    public List<PetInfoDto> findAll() {
        return PetInfoDto.from(petInfoRepository.findAll().stream()
                .filter(info -> !info.getIsDeleted())
                .collect(Collectors.toList()));
    }

    @Override
    public void delete(PetInfoDto petInfoDto) {
        try{
            PetInfo petInfoToDelete = petInfoRepository.findById(petInfoDto.getId())
                    .filter(info -> !info.getIsDeleted())
                    .orElseThrow(EntityNotExistsException::new);
            petInfoToDelete.setIsDeleted(true);
            petInfoRepository.save(petInfoToDelete);
        } catch (Exception ex){
            if (ex instanceof EntityNotExistsException){
                throw ex;
            }
            throw new PersistenceException(ex);
        }
    }

    @Override
    public PetInfoDto add(PetInfoDto petInfoDto) {
        PetInfo newPetInfo = PetInfoDto.to(petInfoDto);
        petInfoRepository.save(newPetInfo);
        return PetInfoDto.from(newPetInfo);
    }

    @Override
    public PetInfoDto findById(Long aLong) {
        return PetInfoDto.from(petInfoRepository.findById(aLong)
                .filter(info -> !info.getIsDeleted())
                .orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public PetInfoDto update(PetInfoDto petInfoDto) {
        PetInfo updatedPetInfo = petInfoRepository.save(PetInfoDto.to(petInfoDto));
        return PetInfoDto.from(updatedPetInfo);
    }

    @Override
    public List<PetInfoDto> findAllByUserId(Long userId) {
        return PetInfoDto.from(petInfoRepository.findAllByUserId(userId)
                        .stream()
                        .filter(info -> !info.getIsDeleted())
                        .collect(Collectors.toList()));
    }
}
