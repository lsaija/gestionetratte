package it.prova.gestionetratte.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import it.prova.gestionetratte.model.Airbus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AirbusDTO {

	private Long id;

	@NotBlank(message = "{codice.notblank}")
	@Size(min = 3, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri")
	private String codice;

	@NotBlank(message = "{descrizione.notblank}")
	private String descrizione;

	@NotNull(message = "{datainizio.notnull}")
	private LocalDate dataInizio;

	@NotNull(message = "{numeropasseggeri.notnull}")
	private Integer numeroPasseggeri;
	
	private Boolean conSovrapposizioni;
	
	@JsonIgnoreProperties(value = { "airbus" })
	private Set<TrattaDTO> tratte = new HashSet<TrattaDTO>(0);

	

	public AirbusDTO() {
		// TODO Auto-generated constructor stub
	}

	public AirbusDTO(Long id) {
		super();
		this.id = id;
	}

	public AirbusDTO(Long id, String codice, String descrizione, LocalDate dataInizio, Integer numeroPasseggeri,
			Set<TrattaDTO> tratte) {
		super();
		this.id = id;
		this.codice = codice;
		this.descrizione = descrizione;
		this.dataInizio = dataInizio;
		this.numeroPasseggeri = numeroPasseggeri;
		this.tratte = tratte;
	}

	public AirbusDTO(String codice, String descrizione) {
		super();
		this.codice = codice;
		this.descrizione = descrizione;
	}

	public AirbusDTO(String codice, String descrizione, LocalDate dataInizio, Integer numeroPasseggeri) {
		super();
		this.codice = codice;
		this.descrizione = descrizione;
		this.dataInizio = dataInizio;
		this.numeroPasseggeri = numeroPasseggeri;
	}

	public AirbusDTO(Long id, String codice, String descrizione, LocalDate dataInizio, Integer numeroPasseggeri) {
		super();
		this.id = id;
		this.codice = codice;
		this.descrizione = descrizione;
		this.dataInizio = dataInizio;
		this.numeroPasseggeri = numeroPasseggeri;
	}

	



	public AirbusDTO(Long id,String codice,String descrizione,LocalDate dataInizio, Integer numeroPasseggeri, Boolean conSovrapposizioni,
			Set<TrattaDTO> tratte) {
		super();
		this.id = id;
		this.codice = codice;
		this.descrizione = descrizione;
		this.dataInizio = dataInizio;
		this.numeroPasseggeri = numeroPasseggeri;
		this.conSovrapposizioni = conSovrapposizioni;
		this.tratte = tratte;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public LocalDate getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(LocalDate dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Integer getNumeroPasseggeri() {
		return numeroPasseggeri;
	}

	public void setNumeroPasseggeri(Integer numeroPasseggeri) {
		this.numeroPasseggeri = numeroPasseggeri;
	}

	public Set<TrattaDTO> getTratte() {
		return tratte;
	}

	public void setTratte(Set<TrattaDTO> tratte) {
		this.tratte = tratte;
	}
	
	public Boolean getConSovrapposizioni() {
		return conSovrapposizioni;
	}

	public void setConSovrapposizioni(Boolean conSovrapposizioni) {
		this.conSovrapposizioni = conSovrapposizioni;
	}

	public Airbus buildAirbusModel() {
		return new Airbus(this.id, this.codice, this.descrizione, this.dataInizio, this.numeroPasseggeri);
	}

	public static AirbusDTO buildAirbusDTOFromModel(Airbus airbusModel, boolean includeTratte) {
		AirbusDTO result = new AirbusDTO(airbusModel.getId(), airbusModel.getCodice(), airbusModel.getDescrizione(),
				airbusModel.getDataInizio(), airbusModel.getNumeroPasseggeri());
		if (includeTratte)
			result.setTratte(TrattaDTO.createTrattaDTOSetFromModelSet(airbusModel.getTratte(), false));
	
			
		return result;
	}

	public static Airbus buildAirbusFromDTO(AirbusDTO airbusDTO, boolean includeTratte) {
		Airbus result = new Airbus(airbusDTO.getId(), airbusDTO.getCodice(), airbusDTO.getDescrizione(),
				airbusDTO.getDataInizio(), airbusDTO.getNumeroPasseggeri());
		if (includeTratte)
			result.setTratte(TrattaDTO.createTrattaSetFromDTOSet(airbusDTO.getTratte(), false));
		
	
		return result;
	}

	public static List<AirbusDTO> createAirbusDTOListFromModelList(List<Airbus> modelListInput, boolean includeTratte) {
		return modelListInput.stream().map(airbusEntity -> {
			AirbusDTO result = AirbusDTO.buildAirbusDTOFromModel(airbusEntity, includeTratte);
			if (includeTratte)
				result.setTratte(TrattaDTO.createTrattaDTOSetFromModelSet(airbusEntity.getTratte(), false));
			
			return result;
			
		}).collect(Collectors.toList());
	}

	public static List<Airbus> createAirbusListFromDTOList(List<AirbusDTO> dtoListInput, boolean includeTratte) {
		return dtoListInput.stream().map(airbusEntity -> {
			Airbus result = AirbusDTO.buildAirbusFromDTO(airbusEntity, includeTratte);
			if (includeTratte)
				result.setTratte(TrattaDTO.createTrattaSetFromDTOSet(airbusEntity.getTratte(), false));
			
			return result;
		}).collect(Collectors.toList());
	}
	
/*	public static AirbusDTO buildAirbusDTOFromModelSovra(Airbus airbusModel, boolean includeTratte,boolean includeSovrapposizioni) {
		AirbusDTO result = new AirbusDTO(airbusModel.getId(), airbusModel.getCodice(), airbusModel.getDescrizione(),
				airbusModel.getDataInizio(), airbusModel.getNumeroPasseggeri());
		if(includeSovrapposizioni)
			result.setConSovrapposizioni(true);
		
		if (includeTratte)
			result.setTratte(TrattaDTO.createTrattaDTOSetFromModelSet(airbusModel.getTratte(), false));

			
		return result;
	}
	
	public static List<AirbusDTO> createAirbusDTOListFromModelListSovra(List<Airbus> modelListInput, boolean includeTratte,boolean includeSovrapposizioni) {
		return modelListInput.stream().map(airbusEntity -> {
			AirbusDTO result = AirbusDTO.buildAirbusDTOFromModelSovra(airbusEntity, includeTratte,includeSovrapposizioni);
			if (includeTratte)
				result.setTratte(TrattaDTO.createTrattaDTOSetFromModelSet(airbusEntity.getTratte(), false));
			if(includeSovrapposizioni)
				result.setConSovrapposizioni(false);
			
			
			return result;
			
		}).collect(Collectors.toList());
	}*/
	
	
	
	
	/*public static boolean haSovrapposizioni(AirbusDTO airbusDTO,List<TrattaDTO> tratteDTO) {
		LocalDate dataItem;
		LocalDate dataItem2;
		LocalTime oraAtterraggioItem;
		LocalTime oraAtterraggioItem2;
		for(TrattaDTO trattaItem : airbusDTO.getTratte()) {
			dataItem=trattaItem.getData();
			oraAtterraggioItem=trattaItem.getOraAtterraggio();
			for(int i=1;i< airbusDTO.getTratte().size()-1;i++) {
				
			}
			
		}
	}*/
}
