define ['base/renderTemplate', 'base/RoomrWidget'],
(renderTemplate, RoomrWidget) ->
  'use strict'

  class LoginWidget extends RoomrWidget
    constructor: () ->
      super('login')
      window.eventMediator.subscribeToEvent 'loggedIn', @renderLoggedIn
      window.eventMediator.subscribeToEvent 'loggedOut', @renderLoggedOut

    addEvents: ->
      $('#LoginWidgetForm').submit (event) ->
        event.preventDefault()
        username = $('#LoginWidgetForm input[name=login]').val()
        passwd = $('#LoginWidgetForm input[name=passwd]').val()
        $.ajax '/rest/login', {
          contentType : "application/json"
          data: "{ \"email\" : \"#{username}\", \"password\" : \"#{passwd}\" }"
          type: 'POST'
          complete: (jqXHR, stat) ->
            if stat == 'success'
              console.log "Hat gepasst"
            else
              console.log "Kaputt", jqXHR
        }
        ###
        $.post '/rest/login',
          "{ \"email\" : \"#{username}\", \"password\" : \"#{passwd}\" }",
          (data) -> console.log data
          ###

    renderInto: (element) ->
      @elem = element

    renderLoggedOut: () =>
      @renderTemplate {}, (html) =>
          $(@elem).append(html)
          @addEvents()

    renderLoggedIn: () =>
      @renderTemplate {}, (html) =>
          $(@elem).append(html)
