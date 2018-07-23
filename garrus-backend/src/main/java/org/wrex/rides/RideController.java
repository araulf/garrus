package org.wrex.rides;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.IterableUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RideController {

	@Autowired
	private RideRepository repo;

	@RequestMapping(value = "/ride/", method = RequestMethod.POST)
	public void addRide(@RequestBody RideDTO ride) {
		Ride ride2 = RideMapper.INSTANCE.dtoToEntity(ride);
		ride2.setPlate(ride.getVehicle().getPlate());
		ride2.setRutChofer(ride.getChofer().getRut());
		repo.save(ride2);
	}
	
	@RequestMapping(value = "/ride/{idRide}", method = RequestMethod.PUT)
	public void updateRide(@RequestBody RideDTO ride) {
		repo.save(RideMapper.INSTANCE.dtoToEntity(ride));
	}

	@RequestMapping("/ride/listAll")
	public List<RideDTO> getAll() {
		return RideMapper.INSTANCE.listToDTOList(IterableUtils.toList(repo.findAll()));
	}
	
	@RequestMapping("/ride/active")
	public List<RideDTO> getActive() {
		Date now = DateTime.now().toDate();
		return RideMapper.INSTANCE.listToDTOList(repo.findByOptionalParameter(null, null, now, now));
//		return RideMapper.INSTANCE.listToDTOList(IterableUtils.toList(repo.findAll()).stream().filter(p->p.getStart().before(now) && p.getEnd().after(now)).collect(Collectors.toList()));
	}
	
	@RequestMapping("/ride/next")
	public List<RideDTO> getNext() {
//		Date now = DateTime.now().toDate();
//		return RideMapper.INSTANCE.listToDTOList(repo.findByOptionalParameter(null, null, now, now));
		return RideMapper.INSTANCE.listToDTOList(IterableUtils.toList(repo.findAll()).stream().filter(p->p.getStart().after(DateTime.now().toDate())).collect(Collectors.toList()));
		
	}
	
	@RequestMapping("/ride/past")
	public List<RideDTO> getPast() {
		return RideMapper.INSTANCE.listToDTOList(repo.findByEndBeforeOrderByStartDesc(DateTime.now().toDate()));
//		return RideMapper.INSTANCE.listToDTOList(IterableUtils.toList(repo.findAll()).stream().filter(p->p.getEnd().before(DateTime.now().toDate())).collect(Collectors.toList()));
		
	}

	@RequestMapping("/ride/{idRide}")
	public RideDTO getById(@PathVariable Integer idRide) {
		return RideMapper.INSTANCE.entityToDTO(repo.findById(idRide).get());
	}
	
	
	@RequestMapping(value="/ride/{idRide}",method=RequestMethod.DELETE)
	public void delate(@PathVariable Integer idRide) {
		repo.deleteById(idRide);
	}


	@RequestMapping("/ride/find")
	public List<RideDTO> find(@RequestParam(value="plate",required=false) String plate, 
							  @RequestParam(value="rutChofer",required=false) String rutChofer,
							  @RequestParam(value="dateStart",required=false) Date dateStart,
							  @RequestParam(value="dateEnd",required=false) Date dateEnd) {
		
		return RideMapper.INSTANCE.listToDTOList(IterableUtils.toList((repo.findByOptionalParameter(plate, rutChofer, dateStart, dateEnd))));
	}

}
