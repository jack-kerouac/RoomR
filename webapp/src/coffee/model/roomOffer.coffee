# This singleton model represents room offers
define ['backbone'], (Backbone) ->
  'use strict'

  class RoomOffer extends Backbone.Model

    defaults: {
      'minAge': '20',
      'maxAge': '40'
    }


  roomOffer = new RoomOffer()
  return roomOffer