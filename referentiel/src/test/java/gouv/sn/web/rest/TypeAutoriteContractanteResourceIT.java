package gouv.sn.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import gouv.sn.IntegrationTest;
import gouv.sn.domain.TypeAutoriteContractante;
import gouv.sn.repository.TypeAutoriteContractanteRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TypeAutoriteContractanteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TypeAutoriteContractanteResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDRE = 1;
    private static final Integer UPDATED_ORDRE = 2;

    private static final String ENTITY_API_URL = "/api/type-autorite-contractantes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TypeAutoriteContractanteRepository typeAutoriteContractanteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeAutoriteContractanteMockMvc;

    private TypeAutoriteContractante typeAutoriteContractante;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeAutoriteContractante createEntity(EntityManager em) {
        TypeAutoriteContractante typeAutoriteContractante = new TypeAutoriteContractante()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION)
            .libelle(DEFAULT_LIBELLE)
            .ordre(DEFAULT_ORDRE);
        return typeAutoriteContractante;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeAutoriteContractante createUpdatedEntity(EntityManager em) {
        TypeAutoriteContractante typeAutoriteContractante = new TypeAutoriteContractante()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .libelle(UPDATED_LIBELLE)
            .ordre(UPDATED_ORDRE);
        return typeAutoriteContractante;
    }

    @BeforeEach
    public void initTest() {
        typeAutoriteContractante = createEntity(em);
    }

    @Test
    @Transactional
    void createTypeAutoriteContractante() throws Exception {
        int databaseSizeBeforeCreate = typeAutoriteContractanteRepository.findAll().size();
        // Create the TypeAutoriteContractante
        restTypeAutoriteContractanteMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeAutoriteContractante))
            )
            .andExpect(status().isCreated());

        // Validate the TypeAutoriteContractante in the database
        List<TypeAutoriteContractante> typeAutoriteContractanteList = typeAutoriteContractanteRepository.findAll();
        assertThat(typeAutoriteContractanteList).hasSize(databaseSizeBeforeCreate + 1);
        TypeAutoriteContractante testTypeAutoriteContractante = typeAutoriteContractanteList.get(typeAutoriteContractanteList.size() - 1);
        assertThat(testTypeAutoriteContractante.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTypeAutoriteContractante.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTypeAutoriteContractante.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testTypeAutoriteContractante.getOrdre()).isEqualTo(DEFAULT_ORDRE);
    }

    @Test
    @Transactional
    void createTypeAutoriteContractanteWithExistingId() throws Exception {
        // Create the TypeAutoriteContractante with an existing ID
        typeAutoriteContractante.setId(1L);

        int databaseSizeBeforeCreate = typeAutoriteContractanteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeAutoriteContractanteMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeAutoriteContractante))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeAutoriteContractante in the database
        List<TypeAutoriteContractante> typeAutoriteContractanteList = typeAutoriteContractanteRepository.findAll();
        assertThat(typeAutoriteContractanteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTypeAutoriteContractantes() throws Exception {
        // Initialize the database
        typeAutoriteContractanteRepository.saveAndFlush(typeAutoriteContractante);

        // Get all the typeAutoriteContractanteList
        restTypeAutoriteContractanteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeAutoriteContractante.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].ordre").value(hasItem(DEFAULT_ORDRE)));
    }

    @Test
    @Transactional
    void getTypeAutoriteContractante() throws Exception {
        // Initialize the database
        typeAutoriteContractanteRepository.saveAndFlush(typeAutoriteContractante);

        // Get the typeAutoriteContractante
        restTypeAutoriteContractanteMockMvc
            .perform(get(ENTITY_API_URL_ID, typeAutoriteContractante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeAutoriteContractante.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.ordre").value(DEFAULT_ORDRE));
    }

    @Test
    @Transactional
    void getNonExistingTypeAutoriteContractante() throws Exception {
        // Get the typeAutoriteContractante
        restTypeAutoriteContractanteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTypeAutoriteContractante() throws Exception {
        // Initialize the database
        typeAutoriteContractanteRepository.saveAndFlush(typeAutoriteContractante);

        int databaseSizeBeforeUpdate = typeAutoriteContractanteRepository.findAll().size();

        // Update the typeAutoriteContractante
        TypeAutoriteContractante updatedTypeAutoriteContractante = typeAutoriteContractanteRepository
            .findById(typeAutoriteContractante.getId())
            .get();
        // Disconnect from session so that the updates on updatedTypeAutoriteContractante are not directly saved in db
        em.detach(updatedTypeAutoriteContractante);
        updatedTypeAutoriteContractante.code(UPDATED_CODE).description(UPDATED_DESCRIPTION).libelle(UPDATED_LIBELLE).ordre(UPDATED_ORDRE);

        restTypeAutoriteContractanteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTypeAutoriteContractante.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTypeAutoriteContractante))
            )
            .andExpect(status().isOk());

        // Validate the TypeAutoriteContractante in the database
        List<TypeAutoriteContractante> typeAutoriteContractanteList = typeAutoriteContractanteRepository.findAll();
        assertThat(typeAutoriteContractanteList).hasSize(databaseSizeBeforeUpdate);
        TypeAutoriteContractante testTypeAutoriteContractante = typeAutoriteContractanteList.get(typeAutoriteContractanteList.size() - 1);
        assertThat(testTypeAutoriteContractante.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypeAutoriteContractante.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTypeAutoriteContractante.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testTypeAutoriteContractante.getOrdre()).isEqualTo(UPDATED_ORDRE);
    }

    @Test
    @Transactional
    void putNonExistingTypeAutoriteContractante() throws Exception {
        int databaseSizeBeforeUpdate = typeAutoriteContractanteRepository.findAll().size();
        typeAutoriteContractante.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeAutoriteContractanteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeAutoriteContractante.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeAutoriteContractante))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeAutoriteContractante in the database
        List<TypeAutoriteContractante> typeAutoriteContractanteList = typeAutoriteContractanteRepository.findAll();
        assertThat(typeAutoriteContractanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTypeAutoriteContractante() throws Exception {
        int databaseSizeBeforeUpdate = typeAutoriteContractanteRepository.findAll().size();
        typeAutoriteContractante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeAutoriteContractanteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeAutoriteContractante))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeAutoriteContractante in the database
        List<TypeAutoriteContractante> typeAutoriteContractanteList = typeAutoriteContractanteRepository.findAll();
        assertThat(typeAutoriteContractanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTypeAutoriteContractante() throws Exception {
        int databaseSizeBeforeUpdate = typeAutoriteContractanteRepository.findAll().size();
        typeAutoriteContractante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeAutoriteContractanteMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeAutoriteContractante))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeAutoriteContractante in the database
        List<TypeAutoriteContractante> typeAutoriteContractanteList = typeAutoriteContractanteRepository.findAll();
        assertThat(typeAutoriteContractanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTypeAutoriteContractanteWithPatch() throws Exception {
        // Initialize the database
        typeAutoriteContractanteRepository.saveAndFlush(typeAutoriteContractante);

        int databaseSizeBeforeUpdate = typeAutoriteContractanteRepository.findAll().size();

        // Update the typeAutoriteContractante using partial update
        TypeAutoriteContractante partialUpdatedTypeAutoriteContractante = new TypeAutoriteContractante();
        partialUpdatedTypeAutoriteContractante.setId(typeAutoriteContractante.getId());

        partialUpdatedTypeAutoriteContractante.code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        restTypeAutoriteContractanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeAutoriteContractante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeAutoriteContractante))
            )
            .andExpect(status().isOk());

        // Validate the TypeAutoriteContractante in the database
        List<TypeAutoriteContractante> typeAutoriteContractanteList = typeAutoriteContractanteRepository.findAll();
        assertThat(typeAutoriteContractanteList).hasSize(databaseSizeBeforeUpdate);
        TypeAutoriteContractante testTypeAutoriteContractante = typeAutoriteContractanteList.get(typeAutoriteContractanteList.size() - 1);
        assertThat(testTypeAutoriteContractante.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypeAutoriteContractante.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTypeAutoriteContractante.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testTypeAutoriteContractante.getOrdre()).isEqualTo(DEFAULT_ORDRE);
    }

    @Test
    @Transactional
    void fullUpdateTypeAutoriteContractanteWithPatch() throws Exception {
        // Initialize the database
        typeAutoriteContractanteRepository.saveAndFlush(typeAutoriteContractante);

        int databaseSizeBeforeUpdate = typeAutoriteContractanteRepository.findAll().size();

        // Update the typeAutoriteContractante using partial update
        TypeAutoriteContractante partialUpdatedTypeAutoriteContractante = new TypeAutoriteContractante();
        partialUpdatedTypeAutoriteContractante.setId(typeAutoriteContractante.getId());

        partialUpdatedTypeAutoriteContractante
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION)
            .libelle(UPDATED_LIBELLE)
            .ordre(UPDATED_ORDRE);

        restTypeAutoriteContractanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeAutoriteContractante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeAutoriteContractante))
            )
            .andExpect(status().isOk());

        // Validate the TypeAutoriteContractante in the database
        List<TypeAutoriteContractante> typeAutoriteContractanteList = typeAutoriteContractanteRepository.findAll();
        assertThat(typeAutoriteContractanteList).hasSize(databaseSizeBeforeUpdate);
        TypeAutoriteContractante testTypeAutoriteContractante = typeAutoriteContractanteList.get(typeAutoriteContractanteList.size() - 1);
        assertThat(testTypeAutoriteContractante.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypeAutoriteContractante.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTypeAutoriteContractante.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testTypeAutoriteContractante.getOrdre()).isEqualTo(UPDATED_ORDRE);
    }

    @Test
    @Transactional
    void patchNonExistingTypeAutoriteContractante() throws Exception {
        int databaseSizeBeforeUpdate = typeAutoriteContractanteRepository.findAll().size();
        typeAutoriteContractante.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeAutoriteContractanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, typeAutoriteContractante.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeAutoriteContractante))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeAutoriteContractante in the database
        List<TypeAutoriteContractante> typeAutoriteContractanteList = typeAutoriteContractanteRepository.findAll();
        assertThat(typeAutoriteContractanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTypeAutoriteContractante() throws Exception {
        int databaseSizeBeforeUpdate = typeAutoriteContractanteRepository.findAll().size();
        typeAutoriteContractante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeAutoriteContractanteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeAutoriteContractante))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeAutoriteContractante in the database
        List<TypeAutoriteContractante> typeAutoriteContractanteList = typeAutoriteContractanteRepository.findAll();
        assertThat(typeAutoriteContractanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTypeAutoriteContractante() throws Exception {
        int databaseSizeBeforeUpdate = typeAutoriteContractanteRepository.findAll().size();
        typeAutoriteContractante.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeAutoriteContractanteMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeAutoriteContractante))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeAutoriteContractante in the database
        List<TypeAutoriteContractante> typeAutoriteContractanteList = typeAutoriteContractanteRepository.findAll();
        assertThat(typeAutoriteContractanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTypeAutoriteContractante() throws Exception {
        // Initialize the database
        typeAutoriteContractanteRepository.saveAndFlush(typeAutoriteContractante);

        int databaseSizeBeforeDelete = typeAutoriteContractanteRepository.findAll().size();

        // Delete the typeAutoriteContractante
        restTypeAutoriteContractanteMockMvc
            .perform(delete(ENTITY_API_URL_ID, typeAutoriteContractante.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeAutoriteContractante> typeAutoriteContractanteList = typeAutoriteContractanteRepository.findAll();
        assertThat(typeAutoriteContractanteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
