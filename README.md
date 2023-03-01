# hotel_management_project
Hotel Management Project

The entitled project is basically a CRUD application which deals with 3 entities (Booking , Customer , Room). Where a customer books a room and automatically the room will be assigned on the basics of the customer requirements.


Mapping is made in such a way that one booking can contain multiples customer and multiple rooms too.

Listing a short description of some methods of Booking Service Class.

1.) addBookingDetails

  1.1) This method will basically first check that every entry made via postman should not be null.
  
  1.2) Also, this method will check internally that atleast one adult should be assigned one/many children else the booking will not be considered.
  
  1.3) Further bill will be automatically got calculated on the basis of the room allocated.

  1.4) An additional discount of 5% will be applied in case the booking is done via online.
  
  1.5) This method also checks for the constraint that if a customer books more than 3 than 50% advance should be paid prior to booking and bill will be calculated for the rest of the amount.

2.) getAllBookingDetails
  
  2.1) This will return all the bookings details.
  
3.) deleteBookingDetails

  3.1)This will delete booking details for a particular ID.
  
4.) updateBooking

  4.1) This will update the booking's details.
  
  
 Further more are the CRUD methods made for the Room and Customer enttities.
