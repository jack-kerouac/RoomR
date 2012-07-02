# A widget is a part of the website. It communicates with other widgets using a
# publish/subscribe pattern.
# Each widget has a name, that is used to look up the template of the widget at
# `widgets/<NAME>`. Concrete widgets need to override the method renderInto that
# takes the element into which the widget should render itself as an argument.

define ['base/EventEmitter.coffee#', 'base/renderTemplate.coffee#'], (EventEmitter, renderTemplate) ->
  'use strict'

  class RoomrWidget extends EventEmitter

    constructor: (@name) ->
      super()
      throw new Error('name is empty') unless @name? || @name == ''

    name: ''

    renderTemplate: (context, callback, name) ->
      if name?
        renderTemplate "widgets/#{name}", context, callback  
      else
        renderTemplate "widgets/#{@name}", context, callback

    renderInto: (element) ->

    subscribeToEvent: (eventName, callback) ->
      window.roomr.eventMediator.subscribeToEvent eventName, callback

    reportError: (errorMsg) ->
      $(window).trigger('widgetError', [@name, errorMsg])
