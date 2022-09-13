package gouv.sn.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import gouv.sn.IntegrationTest;
import gouv.sn.domain.Commune;
import gouv.sn.domain.enumeration.Type;
import gouv.sn.repository.CommuneRepository;
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
 * Integration tests for the {@link CommuneResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CommuneResourceIT {

    private static final String DEFAULT_ADDRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_DENOMINATION = "AAAAAAAAAA";
    private static final String UPDATED_DENOMINATION = "BBBBBBBBBB";

    private static final String DEFAULT_FAXE = "AAAAAAAAAA";
    private static final String UPDATED_FAXE = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO = "AAAAAAAAAA";
    private static final String UPDATED_LOGO = "BBBBBBBBBB";

    private static final String DEFAULT_SIGLE = "AAAAAAAAAA";
    private static final String UPDATED_SIGLE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_URLSITEWEB = "AAAAAAAAAA";
    private static final String UPDATED_URLSITEWEB = "BBBBBBBBBB";

    private static final Type DEFAULT_TYPE = Type.EXEMPLE;
    private static final Type UPDATED_TYPE = Type.EXEMPLE;

    private static final String ENTITY_API_URL = "/api/communes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CommuneRepository communeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCommuneMockMvc;

    private Commune commune;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commune createEntity(EntityManager em) {
        Commune commune = new Commune()
            .addresse(DEFAULT_ADDRESSE)
            .denomination(DEFAULT_DENOMINATION)
            .faxe(DEFAULT_FAXE)
            .logo(DEFAULT_LOGO)
            .sigle(DEFAULT_SIGLE)
            .telephone(DEFAULT_TELEPHONE)
            .urlsiteweb(DEFAULT_URLSITEWEB)
            .type(DEFAULT_TYPE);
        return commune;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commune createUpdatedEntity(EntityManager em) {
        Commune commune = new Commune()
            .addresse(UPDATED_ADDRESSE)
            .denomination(UPDATED_DENOMINATION)
            .faxe(UPDATED_FAXE)
            .logo(UPDATED_LOGO)
            .sigle(UPDATED_SIGLE)
            .telephone(UPDATED_TELEPHONE)
            .urlsiteweb(UPDATED_URLSITEWEB)
            .type(UPDATED_TYPE);
        return commune;
    }

    @BeforeEach
    public void initTest() {
        commune = createEntity(em);
    }

    @Test
    @Transactional
    void createCommune() throws Exception {
        int databaseSizeBeforeCreate = communeRepository.findAll().size();
        // Create the Commune
        restCommuneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commune)))
            .andExpect(status().isCreated());

        // Validate the Commune in the database
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeCreate + 1);
        Commune testCommune = communeList.get(communeList.size() - 1);
        assertThat(testCommune.getAddresse()).isEqualTo(DEFAULT_ADDRESSE);
        assertThat(testCommune.getDenomination()).isEqualTo(DEFAULT_DENOMINATION);
        assertThat(testCommune.getFaxe()).isEqualTo(DEFAULT_FAXE);
        assertThat(testCommune.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testCommune.getSigle()).isEqualTo(DEFAULT_SIGLE);
        assertThat(testCommune.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testCommune.getUrlsiteweb()).isEqualTo(DEFAULT_URLSITEWEB);
        assertThat(testCommune.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void createCommuneWithExistingId() throws Exception {
        // Create the Commune with an existing ID
        commune.setId(1L);

        int databaseSizeBeforeCreate = communeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommuneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commune)))
            .andExpect(status().isBadRequest());

        // Validate the Commune in the database
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCommunes() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        // Get all the communeList
        restCommuneMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commune.getId().intValue())))
            .andExpect(jsonPath("$.[*].addresse").value(hasItem(DEFAULT_ADDRESSE)))
            .andExpect(jsonPath("$.[*].denomination").value(hasItem(DEFAULT_DENOMINATION)))
            .andExpect(jsonPath("$.[*].faxe").value(hasItem(DEFAULT_FAXE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.[*].sigle").value(hasItem(DEFAULT_SIGLE)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].urlsiteweb").value(hasItem(DEFAULT_URLSITEWEB)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    void getCommune() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        // Get the commune
        restCommuneMockMvc
            .perform(get(ENTITY_API_URL_ID, commune.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(commune.getId().intValue()))
            .andExpect(jsonPath("$.addresse").value(DEFAULT_ADDRESSE))
            .andExpect(jsonPath("$.denomination").value(DEFAULT_DENOMINATION))
            .andExpect(jsonPath("$.faxe").value(DEFAULT_FAXE))
            .andExpect(jsonPath("$.logo").value(DEFAULT_LOGO))
            .andExpect(jsonPath("$.sigle").value(DEFAULT_SIGLE))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.urlsiteweb").value(DEFAULT_URLSITEWEB))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCommune() throws Exception {
        // Get the commune
        restCommuneMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCommune() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        int databaseSizeBeforeUpdate = communeRepository.findAll().size();

        // Update the commune
        Commune updatedCommune = communeRepository.findById(commune.getId()).get();
        // Disconnect from session so that the updates on updatedCommune are not directly saved in db
        em.detach(updatedCommune);
        updatedCommune
            .addresse(UPDATED_ADDRESSE)
            .denomination(UPDATED_DENOMINATION)
            .faxe(UPDATED_FAXE)
            .logo(UPDATED_LOGO)
            .sigle(UPDATED_SIGLE)
            .telephone(UPDATED_TELEPHONE)
            .urlsiteweb(UPDATED_URLSITEWEB)
            .type(UPDATED_TYPE);

        restCommuneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCommune.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCommune))
            )
            .andExpect(status().isOk());

        // Validate the Commune in the database
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeUpdate);
        Commune testCommune = communeList.get(communeList.size() - 1);
        assertThat(testCommune.getAddresse()).isEqualTo(UPDATED_ADDRESSE);
        assertThat(testCommune.getDenomination()).isEqualTo(UPDATED_DENOMINATION);
        assertThat(testCommune.getFaxe()).isEqualTo(UPDATED_FAXE);
        assertThat(testCommune.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testCommune.getSigle()).isEqualTo(UPDATED_SIGLE);
        assertThat(testCommune.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testCommune.getUrlsiteweb()).isEqualTo(UPDATED_URLSITEWEB);
        assertThat(testCommune.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingCommune() throws Exception {
        int databaseSizeBeforeUpdate = communeRepository.findAll().size();
        commune.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommuneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, commune.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commune))
            )
            .andExpect(status().isBadRequest());

        // Validate the Commune in the database
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCommune() throws Exception {
        int databaseSizeBeforeUpdate = communeRepository.findAll().size();
        commune.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommuneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(commune))
            )
            .andExpect(status().isBadRequest());

        // Validate the Commune in the database
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCommune() throws Exception {
        int databaseSizeBeforeUpdate = communeRepository.findAll().size();
        commune.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommuneMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(commune)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Commune in the database
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCommuneWithPatch() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        int databaseSizeBeforeUpdate = communeRepository.findAll().size();

        // Update the commune using partial update
        Commune partialUpdatedCommune = new Commune();
        partialUpdatedCommune.setId(commune.getId());

        partialUpdatedCommune
            .denomination(UPDATED_DENOMINATION)
            .faxe(UPDATED_FAXE)
            .logo(UPDATED_LOGO)
            .sigle(UPDATED_SIGLE)
            .telephone(UPDATED_TELEPHONE)
            .type(UPDATED_TYPE);

        restCommuneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommune.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCommune))
            )
            .andExpect(status().isOk());

        // Validate the Commune in the database
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeUpdate);
        Commune testCommune = communeList.get(communeList.size() - 1);
        assertThat(testCommune.getAddresse()).isEqualTo(DEFAULT_ADDRESSE);
        assertThat(testCommune.getDenomination()).isEqualTo(UPDATED_DENOMINATION);
        assertThat(testCommune.getFaxe()).isEqualTo(UPDATED_FAXE);
        assertThat(testCommune.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testCommune.getSigle()).isEqualTo(UPDATED_SIGLE);
        assertThat(testCommune.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testCommune.getUrlsiteweb()).isEqualTo(DEFAULT_URLSITEWEB);
        assertThat(testCommune.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateCommuneWithPatch() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        int databaseSizeBeforeUpdate = communeRepository.findAll().size();

        // Update the commune using partial update
        Commune partialUpdatedCommune = new Commune();
        partialUpdatedCommune.setId(commune.getId());

        partialUpdatedCommune
            .addresse(UPDATED_ADDRESSE)
            .denomination(UPDATED_DENOMINATION)
            .faxe(UPDATED_FAXE)
            .logo(UPDATED_LOGO)
            .sigle(UPDATED_SIGLE)
            .telephone(UPDATED_TELEPHONE)
            .urlsiteweb(UPDATED_URLSITEWEB)
            .type(UPDATED_TYPE);

        restCommuneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCommune.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCommune))
            )
            .andExpect(status().isOk());

        // Validate the Commune in the database
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeUpdate);
        Commune testCommune = communeList.get(communeList.size() - 1);
        assertThat(testCommune.getAddresse()).isEqualTo(UPDATED_ADDRESSE);
        assertThat(testCommune.getDenomination()).isEqualTo(UPDATED_DENOMINATION);
        assertThat(testCommune.getFaxe()).isEqualTo(UPDATED_FAXE);
        assertThat(testCommune.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testCommune.getSigle()).isEqualTo(UPDATED_SIGLE);
        assertThat(testCommune.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testCommune.getUrlsiteweb()).isEqualTo(UPDATED_URLSITEWEB);
        assertThat(testCommune.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingCommune() throws Exception {
        int databaseSizeBeforeUpdate = communeRepository.findAll().size();
        commune.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommuneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, commune.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(commune))
            )
            .andExpect(status().isBadRequest());

        // Validate the Commune in the database
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCommune() throws Exception {
        int databaseSizeBeforeUpdate = communeRepository.findAll().size();
        commune.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommuneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(commune))
            )
            .andExpect(status().isBadRequest());

        // Validate the Commune in the database
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCommune() throws Exception {
        int databaseSizeBeforeUpdate = communeRepository.findAll().size();
        commune.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCommuneMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(commune)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Commune in the database
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCommune() throws Exception {
        // Initialize the database
        communeRepository.saveAndFlush(commune);

        int databaseSizeBeforeDelete = communeRepository.findAll().size();

        // Delete the commune
        restCommuneMockMvc
            .perform(delete(ENTITY_API_URL_ID, commune.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Commune> communeList = communeRepository.findAll();
        assertThat(communeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
