# This singleton model represents room offers
define ['backbone'], (Backbone) ->
  'use strict'

  class RoomOffer extends Backbone.Model

    url: '/rest/flatshares'

    defaults: {
      'street' : 'meine strasse'
      'streetNumber' : '3',
      'zipCode': '82061',
      'city': 'City'
      'gender': 'male',
      'minAge': '20',
      'maxAge': '40',

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

    # TODO (Gernot) mapping for basement etc.(or within backend?)
    toJSON: ->
      json = 
        flatshare:
            address:
              street: @get 'street'
              streetNumber: @get 'streetNumber'
              zipCode: @get 'zipCode'
              city: @get 'city'
          floor: 'basement'
          geoLocation:
            # TODO (Gernot) get it from the form
            latitude: 23123
            longitude : 3123
        streetViewParameters : {}
        smokingTolerance: 'prohibited' 
        typeOfHouse : "old"
        numberOfRooms : @get 'numberOfRooms'
        appliances : @get 'appliances'
        additionalSpaces : ["balcony", "garden"]
        roomOffers : [
          roomDetails : 
            totalRentPerMonthInEuro : @get 'totalRentPerMonthInEuro'
            depositInEuro : @get 'depositInEuro'
            freeFrom : "2012-10-01T00:00:00.000+0100"
            freeTo : "2012-11-30T00:00:00.000+0100"
            roomSize : 
              squareMeters : @get 'roomSize'
          criteria : 
            minAge : @get 'minAge'
            maxAge : @get 'maxAge'
            genders : ['male']
          description : @get 'description'
          contactEmail : @get 'contactEmail'
        ]
      return json



  roomOffer = new RoomOffer()
  return roomOffer



  # "address" : {
# "street" : "Erika-Mann-Straße",
# "streetNumber" : "63",
# "zipCode" : "80636",
# "city" : "München"
# },
# "floor" : "third",
# "geoLocation" : {
# "latitude" : 48.137053,
# "longitude" : 11.584969
# },
# "streetViewParameters" : {},
# "smokingTolerance" : "prohibited",
# "typeOfHouse" : "old",
# "numberOfRooms" : 3,
# "appliances" : ["dishWasher", "tv"],
# "additionalSpaces" : ["balcony", "garden"]
# "roomOffers" : [{
# "roomDetails" : {
#   "totalRentPerMonthInEuro" : 200.0,
#   "depositInEuro" : 400.0,
#   "freeFrom" : "2012-10-01T00:00:00.000+0100",
#   "freeTo" : "2012-11-30T00:00:00.000+0100",
#   "roomSize" : {
#     "squareMeters" : 12.0
#   }
# },
# "criteria" : {
#   "minAge" : 18,
#   "maxAge" : 30,
#   "genders" : [male, female]
# },
# "description" : "Ein super Zimmer",
# "contactEmail" : "bob@example.com"
# }]
# }
