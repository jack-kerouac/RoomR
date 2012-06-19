define ['lib/EventEmitter'], (EventEmitter) ->
  'use strict'

  class StaticRoomrWidget extends EventEmitter
    constructor: (@name, @content) -> 
      if _.isUndefined(@name) || name == ''
        throw new Error('name is empty')
      if _.isUndefined(@content)
        throw new Error('content is undefined')

    name: ''

    content: ''

    renderInto: (element) ->
      element.html(@content)