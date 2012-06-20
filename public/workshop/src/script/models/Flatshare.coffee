# Eine Flatshare beschreibt eine WG
define ['backbone'], (Backbone) ->  Backbone.Model.extend {
  url: -> @get('url')
}