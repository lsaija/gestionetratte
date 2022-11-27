package it.prova.gestionetratte.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionetratte.dto.AirbusDTO;
import it.prova.gestionetratte.dto.TrattaDTO;
import it.prova.gestionetratte.model.Airbus;
import it.prova.gestionetratte.repository.airbus.AirbusRepository;
import it.prova.gestionetratte.web.api.exception.AirbusNotFoundException;
import it.prova.gestionetratte.web.api.exception.AirbusWithTratteException;

@Service
public class AirbusServiceImpl implements AirbusService {
	@Autowired
	private AirbusRepository repository;

	public List<Airbus> listAllElements() {
		return (List<Airbus>) repository.findAll();
	}

	@Override
	public List<Airbus> listAllElementsEager() {
		return (List<Airbus>) repository.findAllEager();
	}

	public Airbus caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	public Airbus caricaSingoloElementoConTratte(Long id) {
		return repository.findByIdEager(id);
	}

	@Transactional
	public Airbus aggiorna(Airbus airbusInstance) {
		return repository.save(airbusInstance);
	}
	


	@Transactional
	public Airbus inserisciNuovo(Airbus airbusInstance) {
		return repository.save(airbusInstance);
	}

	@Transactional
	public void rimuovi(Long idToRemove) {
		repository.findById(idToRemove)
				.orElseThrow(() -> new AirbusNotFoundException("Airbus not found con id: " + idToRemove));
		// condizione airbus con tratte
		AirbusDTO airbusToRemove = AirbusDTO.buildAirbusDTOFromModel(repository.findById(idToRemove).orElse(null),
				true);
		try {
			if (airbusToRemove.getTratte().size() > 0)
				throw new AirbusWithTratteException("Airbus with tratte!");

			repository.deleteById(idToRemove);
		} catch (AirbusWithTratteException e) {
			e.printStackTrace();

		}
	}

	public List<Airbus> findByExample(Airbus example) {
		return repository.findByExample(example);
	}

	public List<Airbus> cercaByCodiceEDescrizioneILike(String term) {
		return repository.findByCodiceIgnoreCaseContainingOrDescrizioneIgnoreCaseContainingOrderByCodiceAsc(term, term);
	}

	public Airbus findByCodiceAndDescrizione(String codice, String descrizione) {
		return repository.findByCodiceAndDescrizione(codice, descrizione);
	}


	@Transactional
	public List<AirbusDTO> cercaAirbusConSovapposizioniDiTratte(List<Airbus> listaAirbus) {
		List<AirbusDTO> resultDTO = AirbusDTO.createAirbusDTOListFromModelList(listaAirbus, true);
		//List<AirbusDTO> result=new ArrayList<AirbusDTO>();
		for (AirbusDTO airbusItem : resultDTO) {
			for (TrattaDTO trattaItem : airbusItem.getTratte()) {
				LocalDate dataItem = trattaItem.getData();
				LocalTime oraDecolloItem = trattaItem.getOraDecollo();
				LocalTime oraAtterraggioItem= trattaItem.getOraAtterraggio();
				for (TrattaDTO trattaItem2 : airbusItem.getTratte()) {
					LocalDate dataItem2 = trattaItem2.getData();
					LocalTime oraAtterraggioItem2 = trattaItem2.getOraAtterraggio();
					LocalTime oraDecolloItem2 = trattaItem2.getOraDecollo();

					
					if (dataItem.equals(dataItem2)) {
						if (((oraDecolloItem.isBefore(oraDecolloItem2))
								&& (oraAtterraggioItem.isAfter(oraAtterraggioItem2)))
								|| ((oraDecolloItem.isBefore(oraAtterraggioItem2))
										&& (oraAtterraggioItem.isAfter(oraAtterraggioItem2)))) {
							airbusItem.setConSovrapposizioni(true);	
						}
					    
				    }
				}
			}
		}


		return resultDTO;

	}

}
