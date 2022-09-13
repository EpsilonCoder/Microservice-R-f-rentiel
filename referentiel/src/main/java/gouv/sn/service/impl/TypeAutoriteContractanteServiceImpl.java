package gouv.sn.service.impl;

import gouv.sn.domain.TypeAutoriteContractante;
import gouv.sn.repository.TypeAutoriteContractanteRepository;
import gouv.sn.service.TypeAutoriteContractanteService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TypeAutoriteContractante}.
 */
@Service
@Transactional
public class TypeAutoriteContractanteServiceImpl implements TypeAutoriteContractanteService {

    private final Logger log = LoggerFactory.getLogger(TypeAutoriteContractanteServiceImpl.class);

    private final TypeAutoriteContractanteRepository typeAutoriteContractanteRepository;

    public TypeAutoriteContractanteServiceImpl(TypeAutoriteContractanteRepository typeAutoriteContractanteRepository) {
        this.typeAutoriteContractanteRepository = typeAutoriteContractanteRepository;
    }

    @Override
    public TypeAutoriteContractante save(TypeAutoriteContractante typeAutoriteContractante) {
        log.debug("Request to save TypeAutoriteContractante : {}", typeAutoriteContractante);
        return typeAutoriteContractanteRepository.save(typeAutoriteContractante);
    }

    @Override
    public TypeAutoriteContractante update(TypeAutoriteContractante typeAutoriteContractante) {
        log.debug("Request to save TypeAutoriteContractante : {}", typeAutoriteContractante);
        return typeAutoriteContractanteRepository.save(typeAutoriteContractante);
    }

    @Override
    public Optional<TypeAutoriteContractante> partialUpdate(TypeAutoriteContractante typeAutoriteContractante) {
        log.debug("Request to partially update TypeAutoriteContractante : {}", typeAutoriteContractante);

        return typeAutoriteContractanteRepository
            .findById(typeAutoriteContractante.getId())
            .map(existingTypeAutoriteContractante -> {
                if (typeAutoriteContractante.getCode() != null) {
                    existingTypeAutoriteContractante.setCode(typeAutoriteContractante.getCode());
                }
                if (typeAutoriteContractante.getDescription() != null) {
                    existingTypeAutoriteContractante.setDescription(typeAutoriteContractante.getDescription());
                }
                if (typeAutoriteContractante.getLibelle() != null) {
                    existingTypeAutoriteContractante.setLibelle(typeAutoriteContractante.getLibelle());
                }
                if (typeAutoriteContractante.getOrdre() != null) {
                    existingTypeAutoriteContractante.setOrdre(typeAutoriteContractante.getOrdre());
                }

                return existingTypeAutoriteContractante;
            })
            .map(typeAutoriteContractanteRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TypeAutoriteContractante> findAll() {
        log.debug("Request to get all TypeAutoriteContractantes");
        return typeAutoriteContractanteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TypeAutoriteContractante> findOne(Long id) {
        log.debug("Request to get TypeAutoriteContractante : {}", id);
        return typeAutoriteContractanteRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeAutoriteContractante : {}", id);
        typeAutoriteContractanteRepository.deleteById(id);
    }
}
