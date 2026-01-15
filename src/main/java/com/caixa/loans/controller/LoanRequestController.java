package com.caixa.loans.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.caixa.loans.dto.CreateLoanRequestRequest;
import com.caixa.loans.dto.LoanRequestResponse;
import com.caixa.loans.dto.UpdateLoanStatusRequest;
import com.caixa.loans.mapper.LoanRequestMapper;
import com.caixa.loans.model.LoanRequest;
import com.caixa.loans.service.LoanRequestService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/loans")
@Tag(name = "Loans", description = "Operaciones sobre solicitudes de préstamo")
public class LoanRequestController {

	private final LoanRequestService service;

	public LoanRequestController(LoanRequestService service) {
		this.service = service;
	}

	@Operation(summary = "Crear una solicitud de préstamo", description = "Crea una nueva solicitud de préstamo (sin persistencia en bbdd)")
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Solicitud creada correctamente", content = @Content(schema = @Schema(implementation = LoanRequestResponse.class))),
			@ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error interno", content = @Content) })
	@PostMapping
	public ResponseEntity<LoanRequestResponse> create(@Valid @RequestBody CreateLoanRequestRequest request,
			UriComponentsBuilder uriBuilder) {
		LoanRequest loanRequest = LoanRequestMapper.toModel(request);
		LoanRequest createdLoanRquest = service.create(loanRequest);

		URI location = uriBuilder.path("/loan/{id}").buildAndExpand(createdLoanRquest.getId()).toUri();

		return ResponseEntity.created(location).body(LoanRequestMapper.toResponse(createdLoanRquest));
	}

	@Operation(summary = "Listar solicitudes de préstamo", description = "Devuelve el listado completo de solicitudes")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Listado obtenido correctamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = LoanRequestResponse.class)))),
			@ApiResponse(responseCode = "500", description = "Error interno", content = @Content) })
	@GetMapping

	public List<LoanRequestResponse> getAll() {
		return service.getAll().stream().map(LoanRequestMapper::toResponse).toList();
	}

	@Operation(summary = "Obtener una solicitud por id", description = "Devuelve una solicitud de préstamo.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Solicitud encontrada", content = @Content(schema = @Schema(implementation = LoanRequestResponse.class))),
			@ApiResponse(responseCode = "404", description = "Solicitud no encontrada", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error interno", content = @Content) })
	@GetMapping("/{id}")
	public LoanRequestResponse getById(@PathVariable Long id) {
		return LoanRequestMapper.toResponse(service.getById(id));
	}

	@Operation(summary = "Cambiar estado de una solicitud", description = "Flujo de estados:\r\n Pendiente -> Aprobada o Rechazada\r\n Aprobada -> Cancelada")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Estado actualizado correctamente", content = @Content(schema = @Schema(implementation = LoanRequestResponse.class))),
			@ApiResponse(responseCode = "400", description = "Estado inválido o transición no permitida", content = @Content),
			@ApiResponse(responseCode = "404", description = "Solicitud no encontrada", content = @Content),
			@ApiResponse(responseCode = "500", description = "Error interno", content = @Content) })
	@PatchMapping("/{id}/status")
	public LoanRequestResponse updateStatus(@PathVariable Long id,
			@Valid @RequestBody UpdateLoanStatusRequest request) {
		return LoanRequestMapper.toResponse(service.updateStatus(id, request.status()));
	}
}
