package com.example.hotel_management.service;

import com.example.hotel_management.entity.Booking;
import com.example.hotel_management.entity.Customer;
import com.example.hotel_management.entity.Room;
import com.example.hotel_management.exception.DataNotFoundException;
import com.example.hotel_management.exception.IdNotFoundException;
import com.example.hotel_management.repository.BookingRepository;
import com.example.hotel_management.repository.CustomerRepository;
import com.example.hotel_management.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class BookingServiceImp implements BookingService{
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomService roomService;
    private int flag = 0;

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private CustomerRepository customerRepository;

    /**
     * to get List of all Bookings done
     * @return List of Booking
     */

    @Override
    public List<Booking> getAllBookingDetails() throws DataNotFoundException {
        List<Booking> bookingList = bookingRepository.findAll();
        if(bookingList.size()==0){
            throw new DataNotFoundException("Enter some details its currently empty!");
        }
        log.info(bookingList.size() + " Total bookings done");
        return bookingList;
    }

    /**
     * to Add a new Booking
     * @param booking - Booking's Details
     * @return Booking's Details
     */
    @Override
    public Booking addBookingDetails(Booking booking) throws DataNotFoundException {

        if(Objects.isNull(booking.getBooking_id()) || Objects.isNull(booking.getDuration()) || booking.getStart_date()==null || booking.getEnd_date()==null || booking.getModeOfBokking()==null
        || booking.getMode_of_payement()==null || booking.getCustomerList().isEmpty() || booking.getRoomList().isEmpty()){
            throw new DataNotFoundException("Some fields are null/missing please check ");
        }
        List<Customer> customers = booking.getCustomerList();
        List<Room> rooms = booking.getRoomList();
        boolean allChild=true;
        if(customers.size()<=1){
            Customer customer1 = customers.get(0);
            if(customer1.getCustomer_age()<18){
                log.warn("Customer entered has age < 18! ");
            }
        }else {
            for (Customer cust : customers) {
                if (cust.getCustomer_age() > 18) {
                    allChild = false;
                }
            }
            if (allChild) {
                log.warn("All customers entered has age < 18! ");
                throw new RuntimeException("All are kids please get some adult with you!");
            }
        }
        double totalRoomPrice = 0;
        for (Room room : rooms){
            totalRoomPrice += room.getPrice_per_day();
        }
        double totalBill ;
        totalBill = booking.getDuration() * totalRoomPrice;

        if(booking.getModeOfBokking().equals("online")){
            log.info("Thanks for choosing online payment you will get an extra 5% discount! ");
            totalBill -= totalBill*0.05;
        }


        if(rooms.size()>3){
            log.info("You need to pay 50% prior that is = "+totalBill*0.5+ " to get this booking done.");
            totalBill=totalBill*0.5;
            booking.setBill_amount(totalBill);
        }else{
            booking.setBill_amount(totalBill);
        }

        log.info("Booking details are added.");
        return bookingRepository.save(booking);
    }


    /**
     * to get Bookings details with Booking's ID
     *
     * @param id - Booking's ID
     * @return Booking's Details
     */
    @Override
    public Booking getBookingDetailsById(long id) throws IdNotFoundException {
        Optional<Booking> booking = bookingRepository.findById(id);
        if(booking.isEmpty()){
            throw new IdNotFoundException("Given ID not found");
        }
        return booking.get();

    }

    /**
     * to Delete a Booking details
     * this will restore the existing room back
     *
     * @param id - booking's ID
     */
    @Override
    public void deleteBookingDetails(long id) throws IdNotFoundException, DataNotFoundException {
        Optional<Booking> booking = bookingRepository.findById(id);
        List<Room> rooms = booking.get().getRoomList();
        if(booking.isEmpty()){
            throw new IdNotFoundException("No ID present with ID "+id);
        }
        log.info("Booking details for ID " + id + " deleted successfully! ");
        bookingRepository.deleteById(id);
        for(Room room : rooms){
            room.setAvailability(true);
            room.setChecked(false);
            room.setCheckedOut(true);
            roomRepository.save(room);
        }
    }
    /**
     * to Update Booking Details
     *
     * @param id - Booking's ID
     * @param booking - Booking's Details
     * @return Booking's Details
     */
    @Override
    public Booking updateBooking(long id, Booking booking) {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        if(bookingOptional.isEmpty()){
            throw new RuntimeException("Id not found please check! ");
        }
        double newBill = 0;
        long newDuration = booking.getDuration();
        List<Room> room = booking.getRoomList();
        for(Room room1 : room){
            newBill = newDuration * room1.getPrice_per_day();
            log.info("After updating the revised bill is "+ newBill);
        }
        Booking existingBooking = bookingOptional.get();
        existingBooking.setDuration(booking.getDuration());
        existingBooking.setStart_date(booking.getStart_date());
        existingBooking.setEnd_date(booking.getEnd_date());
        existingBooking.setCustomerList(booking.getCustomerList());
        existingBooking.setRoomList(booking.getRoomList());
        existingBooking.setModeOfBokking(booking.getModeOfBokking());
        existingBooking.setBill_amount(newBill);
        existingBooking.setMode_of_payement(booking.getMode_of_payement());
        System.out.println(existingBooking);
        log.info("Booking details updated successfully!");
        return bookingRepository.save(existingBooking);
    }

    /**
     * to allocate room on basis of type of room preferred { Simple, luxury , deluxe }
     *
     * @param booking   - Booking's Details
     * @param roomList  - Room's Details
     * @return Booking's Details
     */
    @Override
    public Booking methodToAllocateRoom(Booking booking, List<Room> roomList) throws DataNotFoundException {
        if(flag==0){
            roomService.addDefaultroom();
            flag++;
        }
        String roomRequired = booking.getTypeOfRoomPrefered();
        List<Room> ansRooms = roomRepository.findRoomByTypeAndAvailability(roomRequired,true);
        int i = 0;
        int count = countCustomers(booking);
        if(ansRooms.isEmpty()){
            throw new DataNotFoundException("NO ROOMS LEFT ALL ARE CURRENTLY FILLED! ");
        }
            for(Room roomans : ansRooms) {
                if(roomans.isAvailability()==true) {
                    roomList.add(ansRooms.get(i));
                    for (Room room : roomList) {
                        room.setAvailability(false);
                        room.setChecked(true);
                        room.setCheckedOut(false);
                    }
                    booking.setRoomList(roomList);
                    break;
                } else if (!ansRooms.isEmpty()) {
                    i++;
                    continue;
                } else {
                    throw new DataNotFoundException("No empty room");
                }
            }
        return booking;
    }

    /**
     * to Count number of customers in a particular Booking
     *
     * @param booking - Booking's ID
     */
    @Override
    public int countCustomers(Booking booking) {
        return booking.getCustomerList().size();
    }

    /**
     * to allocate number of rooms on basis of customer count
     *
     * @param booking   - Booking's Details
     * @return Booking's Details
     */

    @Override
    public Booking getRoomsToAllocate(Booking booking) throws DataNotFoundException {
        int count = 0;
        count+= countCustomers(booking);
        List<Room> roomList = new ArrayList<>();
        Booking modifiedBooking = new Booking();
        System.out.println("Count : "+count);
        int roomNeeded = count/2;
        System.out.println("Room needed : " + roomNeeded);
        if(count == 1){
            roomNeeded = 1;
        }
        while(roomNeeded>0){
            modifiedBooking = new Booking();
            modifiedBooking = methodToAllocateRoom(booking, roomList);
            roomNeeded--;
        }
        return modifiedBooking;
    }


}
