define ['lib/EventEmitter', 'lib/renderTemplate'], (EventEmitter, renderTemplate) ->
  'use strict'

  class TemplatedRoomrWidget extends EventEmitter

    constructor: (@name, @context) -> 
      if _.isUndefined(@name) || name == ''
        throw new Error('name is empty')
      if _.isUndefined(@context)
        throw new Error('context is undefined')

    name: ''

    context: {}

    renderInto: (element) ->
      renderTemplate "widgets/#{@name}", @context, (content) -> element.html(content)