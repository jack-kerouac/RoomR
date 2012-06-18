# View für Einträge
# -----------------

#
define ['backbone', 'lib/formatPriority'], (Backbone, formatPriority) -> 

  'use strict'

  return Backbone.View.extend {

    template: _.template('<span id="list-item-<%= id %>" class="item-title"><%= title %></span>
              (Priorität: <span class="priority"><%= priority %></span>)')

    # Für das Rendering verwenden wir das übergebene Template und hängen das fertige
    # Element direkt in `target` ein.
    render: (target) ->
      target = $(target) unless target instanceof jQuery
      html = @template {
        id: @model.get('id')
        title: @model.get('title')
        priority: formatPriority @model.get('priority') # Schön formatiert
      }
      @$el.html(html).appendTo(target)
      return this

  }
