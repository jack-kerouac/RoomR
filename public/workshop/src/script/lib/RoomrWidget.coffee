# A widget is a part of the website. It communicates with other widgets using a
# publish/subscribe pattern.
# Each widget has a name, that is used to look up the template of the widget at
# `widgets/<NAME>`. Concrete widgets need to override the method renderInto that
# takes the element into which the widget should render itself as an argument.

define ['lib/EventEmitter', 'lib/renderTemplate'], (EventEmitter, renderTemplate) ->
  'use strict'

  class RoomrWidget extends EventEmitter

    constructor: (@name) ->
      super()
      if _.isUndefined(@name) || name == ''
        throw new Error('name is empty')

    renderTemplate: (context, callback) ->
      renderTemplate "widgets/#{@name}", context, callback

    renderInto: (element) ->