# Router
# ------

# Im Router werden URLs auf Funktionen gemappt und auch die Funktionen selbst definiert.
# In unserem Fall machen diese Funktionen nichts weiter, als die entspechenden
# Änderungen im Page-Model auszulösen. Auf diese Änderungen können dann wiederum andere
# Teile der Applikation reagieren
define ['lib/backbone'], (Backbone) -> Backbone.Router.extend {

  initialize: ->
    @allRoutes = new Array()
    @currentIndex = 0
    router= this
    $(document).ready ->
      $('body').click (event) ->
        el = $(event.target)
        if el.prop('nodeName').toLowerCase() == 'a'
          link = el.attr('href')
          if link? and link != '' and link[0] == '/'
            event.preventDefault()
            @currentroute = link
            router.navigate link, { trigger : yes }

  navigateNext: ->
    maxRoutes = @allRoutes.length
    @currentIndex = ++@currentIndex % maxRoutes
    @navigate @allRoutes[@currentIndex], { trigger : yes }

  navigateBack: ->
    maxRoutes = @allRoutes.length
    @currentIndex = if @currentIndex > 0 then @currentIndex-1 else maxRoutes-1
    @navigate @allRoutes[@currentIndex], { trigger : yes }

  # TODO: Flo: handle unexpected hash tags
  addSection: (section) ->
    @route section.path, section.name, -> section.render()
    @allRoutes.push section.path

}