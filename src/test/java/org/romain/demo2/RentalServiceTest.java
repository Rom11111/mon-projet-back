package org.romain.demo2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.romain.demo2.dao.ProductDao;
import org.romain.demo2.dao.RentalDao;
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

    // Mocks nécessaires pour tester RentalService sans base de données
    private RentalService rentalService;
    private RentalDao rentalDao;
    private ProductDao productDao;
    private UserDao userDao;

    // Avant chaque test, je crée des mocks pour injecter dans le service
    @BeforeEach
    void setUp() {
        rentalDao = mock(RentalDao.class);
        productDao = mock(ProductDao.class);
        userDao = mock(UserDao.class);
        rentalService = new RentalService(rentalDao, productDao, userDao);
    }

    // Cas : produit inexistant → doit échouer
    @Test
    void shouldFail_ifProductNotFound() {
        RentalRequestDto dto = validDto();
        when(productDao.findById(dto.getProductId())).thenReturn(Optional.empty());
        when(userDao.findById(1)).thenReturn(Optional.of(validClient()));

        BusinessException ex = assertThrows(BusinessException.class, () ->
                rentalService.createRental(dto, 1));

        assertEquals("Produit introuvable", ex.getMessage());
    }

    // Cas : client introuvable → doit échouer
    @Test
    void shouldFail_ifClientNotFound() {
        RentalRequestDto dto = validDto();
        when(productDao.findById(dto.getProductId())).thenReturn(Optional.of(new Product()));
        when(userDao.findById(1)).thenReturn(Optional.empty());

        BusinessException ex = assertThrows(BusinessException.class, () ->
                rentalService.createRental(dto, 1));

        assertEquals("Client introuvable", ex.getMessage());
    }

    // Cas : utilisateur avec mauvais rôle (pas CLIENT) → doit échouer
    @Test
    void shouldFail_ifUserIsNotClient() {
        RentalRequestDto dto = validDto();
        when(productDao.findById(dto.getProductId())).thenReturn(Optional.of(new Product()));

        User admin = new User();
        admin.setId(1);
        admin.setRole(Role.ADMIN); // mauvais rôle
        when(userDao.findById(1)).thenReturn(Optional.of(admin));

        BusinessException ex = assertThrows(BusinessException.class, () ->
                rentalService.createRental(dto, 1));

        assertEquals("Seuls les clients peuvent réserver des produits", ex.getMessage());
    }

    // Cas : date de début après la date de fin → doit échouer
    @Test
    void shouldFail_ifStartDateAfterEndDate() {
        RentalRequestDto dto = validDto();
        dto.setStartDate(LocalDate.now().plusDays(5));
        dto.setEndDate(LocalDate.now().plusDays(1)); // incohérent

        when(productDao.findById(dto.getProductId())).thenReturn(Optional.of(new Product()));
        when(userDao.findById(1)).thenReturn(Optional.of(validClient()));

        BusinessException ex = assertThrows(BusinessException.class, () ->
                rentalService.createRental(dto, 1));

        assertEquals("La date de début doit être avant la date de fin", ex.getMessage());
    }

    // Cas : produit déjà réservé sur cette période → doit échouer
    @Test
    void shouldFail_ifProductAlreadyReserved() {
        RentalRequestDto dto = validDto();

        Product product = new Product();
        product.setId(dto.getProductId()); // Obligatoire pour le test

        when(productDao.findById(dto.getProductId())).thenReturn(Optional.of(product));
        when(userDao.findById(1)).thenReturn(Optional.of(validClient()));

        // Simule une réservation existante (donc conflit)
        when(rentalDao.findByProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                eq(dto.getProductId()), any(), any()
        )).thenReturn(List.of(new Rental()));

        BusinessException ex = assertThrows(BusinessException.class, () ->
                rentalService.createRental(dto, 1));

        assertEquals("Le produit est déjà réservé sur cette période", ex.getMessage());
    }

    // Cas : tout est OK → location créée avec succès
    @Test
    void shouldSucceed_ifRequestIsValid() {
        RentalRequestDto dto = validDto();

        Product product = new Product();
        product.setId(dto.getProductId());

        User client = validClient();

        // On simule un produit et un client valides
        when(productDao.findById(dto.getProductId())).thenReturn(Optional.of(product));
        when(userDao.findById(client.getId())).thenReturn(Optional.of(client));

        // Simule que le produit est dispo (pas de conflit)
        when(rentalDao.findByProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                anyInt(), any(), any())
        ).thenReturn(List.of());

        // Simule que la location est bien enregistrée
        Rental saved = new Rental();
        saved.setId(99L);
        saved.setClient(client);
        saved.setProduct(product);
        saved.setStartDate(dto.getStartDate());
        saved.setEndDate(dto.getEndDate());
        saved.setStatus(RentalStatus.PENDING);

        when(rentalDao.save(any(Rental.class))).thenReturn(saved);

        Rental result = rentalService.createRental(dto, client.getId());

        // Je vérifie que tout est correct
        assertNotNull(result);
        assertEquals(product, result.getProduct());
        assertEquals(client, result.getClient());
        assertEquals(RentalStatus.PENDING, result.getStatus());
    }

    // --- Méthodes utilitaires internes pour éviter les répétitions ---

    // Je crée une demande de location valide
    private RentalRequestDto validDto() {
        RentalRequestDto dto = new RentalRequestDto();
        dto.setProductId(1);
        dto.setStartDate(LocalDate.now().plusDays(1));
        dto.setEndDate(LocalDate.now().plusDays(3));
        return dto;
    }

    // Je crée un client avec un rôle valide
    private User validClient() {
        User user = new User();
        user.setId(1);
        user.setRole(Role.CLIENT);
        return user;
    }
}
