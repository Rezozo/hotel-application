package com.hotel.app.service.impl;

import com.hotel.app.dto.BookingInfoDto;
import com.hotel.app.models.Room;
import com.hotel.app.service.BookingService;
import com.hotel.app.models.Booking;
import com.hotel.app.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class BookingServiceImpl implements BookingService {
    private BookingRepository bookingRepository;
    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }
    @Override
    public List<Date> getArrivalDates(Integer id) {
        return bookingRepository.findBookingArrivalByRoomId(id);
    }
    @Override
    public List<Date> getDepartureDates(Integer id) {
        return bookingRepository.findBookingDepartureByRoomId(id);
    }
    @Override
    public List<BookingInfoDto> getByPhoneNumber(String phoneNumber) {
        return bookingRepository.findBookingInfoOne(phoneNumber);
    }
    @Override
    public List<Map<String, Integer>> getStats(Boolean thisMonth) {
        if(thisMonth != null) {
            return bookingRepository.findStatsBookingThisMonth();
        }
        return bookingRepository.findStatsBooking();
    }

    @Override
    public List<BookingInfoDto> getAll(String cost, String arrivalDate) {
        if (cost == null && arrivalDate != null) {
            return bookingRepository.findBookingInfoAllOrderByArrival(arrivalDate);
        } else if (cost != null && arrivalDate == null) {
            return bookingRepository.findBookingInfoAllOrderByPrice(cost);
        }
        return bookingRepository.findBookingInfoAll();
    }

    @Override
    public void save(Booking booking) {
        bookingRepository.save(booking);
    }

    @Override
    public void deleteById(Integer id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public Boolean canBook(BookingInfoDto bookingInfoDto, List<Date> arrivalDates, List<Date> departureDates) {
        for (int i = 0; i < arrivalDates.size(); i++) {
            if (!bookingInfoDto.getDepartureDate().before(arrivalDates.get(i))
                    && !bookingInfoDto.getArrivalDate().after(departureDates.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean canBookInThisSegment(Date arrivalDate, Date departureDate, List<Date> arrivalDates, List<Date> departureDates) {
        for (int i = 0; i < arrivalDates.size(); i++) {
            if (!departureDate.before(arrivalDates.get(i))
                    && !arrivalDate.after(departureDates.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Integer getCost(BookingInfoDto bookingInfoDto, Room room) {
        int costPerDay = room.getPrice();
        Date arrivalDate = bookingInfoDto.getArrivalDate();
        Date departureDate = bookingInfoDto.getDepartureDate();

        Duration duration = Duration.between(arrivalDate.toInstant(), departureDate.toInstant());
        long days = duration.toDays();
        if(days == 0) days++;
        return (int) days * costPerDay;
    }
}
