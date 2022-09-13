package gouv.sn.service.impl;

import gouv.sn.domain.Commune;
import gouv.sn.repository.CommuneRepository;
import gouv.sn.service.CommuneService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Commune}.
 */
@Service
@Transactional
public class CommuneServiceImpl implements CommuneService {

    private final Logger log = LoggerFactory.getLogger(CommuneServiceImpl.class);

    private final CommuneRepository communeRepository;

    public CommuneServiceImpl(CommuneRepository communeRepository) {
        this.communeRepository = communeRepository;
    }

    @Override
    public Commune save(Commune commune) {
        log.debug("Request to save Commune : {}", commune);
        return communeRepository.save(commune);
    }

    @Override
    public Commune update(Commune commune) {
        log.debug("Request to save Commune : {}", commune);
        return communeRepository.save(commune);
    }

    @Override
    public Optional<Commune> partialUpdate(Commune commune) {
        log.debug("Request to partially update Commune : {}", commune);

        return communeRepository
            .findById(commune.getId())
            .map(existingCommune -> {
                if (commune.getAddresse() != null) {
                    existingCommune.setAddresse(commune.getAddresse());
                }
                if (commune.getDenomination() != null) {
                    existingCommune.setDenomination(commune.getDenomination());
                }
                if (commune.getFaxe() != null) {
                    existingCommune.setFaxe(commune.getFaxe());
                }
                if (commune.getLogo() != null) {
                    existingCommune.setLogo(commune.getLogo());
                }
                if (commune.getSigle() != null) {
                    existingCommune.setSigle(commune.getSigle());
                }
                if (commune.getTelephone() != null) {
                    existingCommune.setTelephone(commune.getTelephone());
                }
                if (commune.getUrlsiteweb() != null) {
                    existingCommune.setUrlsiteweb(commune.getUrlsiteweb());
                }
                if (commune.getType() != null) {
                    existingCommune.setType(commune.getType());
                }

                return existingCommune;
            })
            .map(communeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Commune> findAll(Pageable pageable) {
        log.debug("Request to get all Communes");
        return communeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Commune> findOne(Long id) {
        log.debug("Request to get Commune : {}", id);
        return communeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Commune : {}", id);
        communeRepository.deleteById(id);
    }
}
