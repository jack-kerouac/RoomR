define ['base/renderTemplate', 'base/RoomrWidget'],
(renderTemplate, RoomrWidget) ->
  'use strict'

  class CreateUserWidget extends RoomrWidget
    constructor: () ->
      super('createUser')
      @loginState = 'unknown'

      @registerEvent 'newUserCreated'
      @subscribeToEvent 'loginStateChanged',
        (newState) => @loginState = newState; @render()

    setCreateUserSubmitEvent: () ->
      $('#CreateUserWidgetForm').submit (event) =>
        event.preventDefault()
        day = $('#CreateUserWidgetForm input[name=birthdate_day]').val()
        mon = $('#CreateUserWidgetForm input[name=birthdate_mon]').val()
        year = $('#CreateUserWidgetForm input[name=birthdate_year]').val()
        userToCreate = {
          name: $('#CreateUserWidgetForm input[name=name]').val(),
          email: $('#CreateUserWidgetForm input[name=email]').val(),
          password: $('#CreateUserWidgetForm input[name=password]').val(),
          birthdate: "#{year}-#{mon}-#{day}T12:00:00.000+0100",
          gender: $('#CreateUserWidgetForm input[name=gender]').val(),
        }
        $.ajax '/rest/users', {
          contentType : "application/json"
          data: JSON.stringify userToCreate
          type: 'POST'
          complete: (jqXHR, stat) =>
            if stat == 'success'
              @emit 'newUserCreated', userToCreate
            else
              alert "Irgendwas ging schief. Sorry..."
        }

    renderInto: (@elem) -> @render()

    render: () ->
      if @elem?
        if @loginState == 'loggedOut'
          @renderVisibleForm()
        else
          @renderInvisibleForm()

    renderInvisibleForm: () =>
      @renderTemplate {}, (html) => $(@elem).empty()

    renderVisibleForm: () =>
      @renderTemplate { }, (html) =>
        $(@elem).append(html)
        @setCreateUserSubmitEvent()
