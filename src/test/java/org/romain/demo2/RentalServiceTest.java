package org.romain.demo2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.romain.demo2.dao.ProductDao;
import org.romain.demo2.dao.RentalDao;
import org.romain.demo2.dao.ReportDao;
import org.romain.demo2.dao.UserDao;
import org.romain.demo2.dto.RentalRequestDto;
import org.romain.demo2.exception.BusinessException;
import org.romain.demo2.model.*;
import org.romain.demo2.service.RentalService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RentalServiceTest {

    private RentalService rentalService;
    private RentalDao rentalDao;
    private ProductDao productDao;
    private UserDao userDao;
    private ReportDao reportDao;

    @BeforeEach
    void setUp() {
        rentalDao = mock(RentalDao.class);
        productDao = mock(ProductDao.class);
        userDao = mock(UserDao.class);
        reportDao = mock(ReportDao.class);
        rentalService = new RentalService(rentalDao, productDao, userDao, reportDao);
    }

    // Cas : produit déjà réservé (stock insuffisant) → doit échouer
    @Test
    void shouldFail_ifStockInsufficient() {
        RentalRequestDto dto = validDto();
        dto.setQuantity(2); // demande 2 unités

        Product product = new Product();
        product.setId(dto.getProductId());
        product.setStock(2); // stock total = 2
        when(productDao.findById(dto.getProductId())).thenReturn(Optional.of(product));
        when(userDao.findById(1L)).thenReturn(Optional.of(validClient()));

        // Simule qu’il y a déjà 2 unités réservées sur la période
        when(rentalDao.sumQuantityForProductBetweenDates(eq(dto.getProductId()), any(), any()))
                .thenReturn(2);

        BusinessException ex = assertThrows(BusinessException.class, () ->
                rentalService.createRental(dto, 1L));

        assertTrue(ex.getMessage().contains("Stock insuffisant"));
    }


    // Cas : client introuvable → doit échouer
    @Test
    void shouldFail_ifClientNotFound() {
        RentalRequestDto dto = validDto();
        Product product = new Product();
        product.setStock(5); // ✅ nécessaire
        when(productDao.findById(dto.getProductId())).thenReturn(Optional.of(product));
        when(userDao.findById(1L)).thenReturn(Optional.empty());

        BusinessException ex = assertThrows(BusinessException.class, () ->
                rentalService.createRental(dto, 1L));

        assertEquals("Client introuvable", ex.getMessage());
    }

    // Cas : utilisateur avec mauvais rôle (pas CLIENT) → doit échouer
    @Test
    void shouldFail_ifUserIsNotClient() {
        // je prépare une demande de location "valide"
        RentalRequestDto dto = new RentalRequestDto();
        dto.setProductId(1L);
        dto.setStartDate(LocalDate.now().plusDays(1)); // demain
        dto.setEndDate(LocalDate.now().plusDays(3));   // dans 3 jours
        dto.setQuantity(1); // quantité demandée

        // je crée un produit dispo avec stock
        Product product = new Product();
        product.setStock(5);
        when(productDao.findById(1L)).thenReturn(Optional.of(product));

        // je crée un utilisateur mais en rôle ADMIN (pas CLIENT)
        User admin = new User();
        admin.setId(1L);
        admin.setRole(Role.ADMIN); // rôle incorrect
        when(userDao.findById(1L)).thenReturn(Optional.of(admin));

        // j'appelle le service et je vérifie qu'il renvoie bien une erreur
        BusinessException ex = assertThrows(BusinessException.class, () ->
                rentalService.createRental(dto, 1L));

        // je vérifie que le message est bien celui attendu
        assertEquals("Seuls les clients peuvent réserver des produits", ex.getMessage());
    }

    // Si la date de début est après la date de fin → ça doit échouer
    @Test
    void shouldFail_ifStartDateAfterEndDate() {
        RentalRequestDto dto = new RentalRequestDto();
        dto.setProductId(1L);
        dto.setStartDate(LocalDate.now().plusDays(5)); // début dans 5 jours
        dto.setEndDate(LocalDate.now().plusDays(1));   // fin avant le début
        dto.setQuantity(1);

        Product product = new Product();
        product.setStock(5); // stock dispo
        when(productDao.findById(1L)).thenReturn(Optional.of(product));
        when(userDao.findById(1L)).thenReturn(Optional.of(validClient()));

        // Ici on vérifie bien que le service renvoie une erreur claire
        BusinessException ex = assertThrows(BusinessException.class, () ->
                rentalService.createRental(dto, 1L));

        assertEquals("La date de début doit être avant la date de fin", ex.getMessage());
    }



    // Cas : tout est OK → location créée avec succès
    @Test
    void shouldSucceed_ifRequestIsValid() {
        RentalRequestDto dto = validDto();
        dto.setQuantity(2); // ✅ quantité demandée

        Product product = new Product();
        product.setId(dto.getProductId());
        product.setStock(10); // ✅ stock dispo

        User client = validClient();

        when(productDao.findById(dto.getProductId())).thenReturn(Optional.of(product));
        when(userDao.findById(client.getId())).thenReturn(Optional.of(client));

        // Aucun conflit de réservation
        when(rentalDao.sumQuantityForProductBetweenDates(anyLong(), any(), any()))
                .thenReturn(0);

        Rental saved = new Rental();
        saved.setId(99L);
        saved.setClient(client);
        saved.setProduct(product);
        saved.setStartDate(dto.getStartDate());
        saved.setEndDate(dto.getEndDate());
        saved.setQuantity(dto.getQuantity());
        saved.setStatus(RentalStatus.PENDING);

        when(rentalDao.save(any(Rental.class))).thenReturn(saved);

        Rental result = rentalService.createRental(dto, client.getId());

        assertNotNull(result);
        assertEquals(product, result.getProduct());
        assertEquals(client, result.getClient());
        assertEquals(2, result.getQuantity());
        assertEquals(RentalStatus.PENDING, result.getStatus());
    }

    // --- Méthodes utilitaires ---
    private RentalRequestDto validDto() {
        RentalRequestDto dto = new RentalRequestDto();
        dto.setProductId(1L);
        dto.setStartDate(LocalDate.now().plusDays(1));
        dto.setEndDate(LocalDate.now().plusDays(3));
        dto.setQuantity(1); // ✅ quantité par défaut
        return dto;
    }

    private User validClient() {
        User user = new User();
        user.setId(1L);
        user.setRole(Role.CLIENT);
        return user;
    }
}
