# This singleton model represents room offers
define ['backbone'], (Backbone) ->
  'use strict'

  class RoomOffer extends Backbone.Model

    defaults: {
      'gender': 'male',
      'minAge': '20',
      'maxAge': '40',
      'street' : '',
      'postalCode': '',
      'city': '',
      'floor': '4.OG',
      'floorOptions' : ['1.OG','2.OG','3.OG','4.OG','5.OG','6.OG','7.OG','8.OG','9.OG','10.OG'],
      'smokingToleranceOptions' : ['in der ganzen Wohnung erlaubt', 'nur im Zimmer erlaubt',
        'Nichtraucher WG']
      'smokingTolerance' : 'Nichtraucher WG',
      'numberOfRooms' : 2,
      'typeOfHouseOptions' : ['Alt', 'Renoviert', 'Neu'],
      'typeOfHouse' : 'Alt',
      'gadgetOptions' : ['dishwasher', 'washing mashine', 'tv'],
      'gadgets' : [],
      'totalRentPerMonthInEuro' : 0,
      'depositInEuro' : 0,
      'roomSize' : 0,
      'freeFrom' : 'Jetzt',
      'freeTo' : 'Unbegrenzt',
      'description' : '',
      'contactEmail' : ''
    }

  roomOffer = new RoomOffer()
  return roomOffer