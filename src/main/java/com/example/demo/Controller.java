package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@org.springframework.stereotype.Controller
public class Controller {
    private static int REVENU = 0;
    private static final int POURCENTAGE = 5;
    @Autowired
    HotelClient hotelClient;
    @Autowired
    PalaceClient palaceClient;

    @GetMapping("/")
    public String home(Model model)
    {
model.addAttribute("request" , new GetHotelRequest());
        return "index";
    }
 @GetMapping("/getHotels")
    public String getAllHotelDispo(@ModelAttribute("request") GetHotelRequest request , Model model)
 {
   GetHotelResponse responses =   hotelClient.getChambres(request);

   GetHotelResponse response1 = palaceClient.getChambres(request);
     List<BookList> newList = Stream.concat(responses.getList().stream(), response1.getList().stream())
             .collect(Collectors.toList());

newList.forEach(bookList -> bookList.setPrix(bookList.getPrix() + POURCENTAGE));//le prix de cette agence(prix base + 3)
     model.addAttribute("listBook" , newList);
     model.addAttribute("reserver" , new GetReservationRequest());
   return "lists";
 }
    @PostMapping("/choisir")
    public String choisir(@ModelAttribute("reserver") GetReservationRequest request,  Model model)
    {
        System.out.println("heyooooo" + request.getOffreId());
        model.addAttribute("finalRes" , request);
        return "reserver";
    }
    @PostMapping("/reserve")
    public String confirm(@ModelAttribute("finalRes") GetReservationRequest request , Model model)
    {
        GetReservationResponse response;
        if (request.getOffreId().contains("A")) {
           response = hotelClient.getReservation(request);
           if (response.confirmation.contains("Vous avez réservé")) {
               REVENU = REVENU + POURCENTAGE;
               model.addAttribute("respo" , response.confirmation);
               return "fin";
           }
        } else {
             response = palaceClient.getReservation(request);
            if (response.confirmation.contains("Vous avez réservé")) {
                REVENU = REVENU + POURCENTAGE;
                model.addAttribute("respo" , response.confirmation);
                return "fin";
            }
        }
        model.addAttribute("respo" , response.confirmation);
return "echec";
    }
    @GetMapping("/revenu")//dans une réal application seulement le admin a la permission d'acceder cette fonction
    public int getRevenu() {
       return REVENU;
    }

    }
