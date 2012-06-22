# Router
# ------

# Im Router werden URLs auf Funktionen gemappt und auch die Funktionen selbst definiert.
# In unserem Fall machen diese Funktionen nichts weiter, als die entspechenden
# Änderungen im Page-Model auszulösen. Auf diese Änderungen können dann wiederum andere
# Teile der Applikation reagieren
define ['backbone', 'jquerymobile'], (Backbone) -> Backbone.Router.extend {

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
    @configureSwiping()
    $(window).bind 'swiperight', -> router.navigateBack.call(router)
    $(window).bind 'swipeleft', -> router.navigateNext.call(router)

  configureSwiping: ->
    $.event.special.swipe.scrollSupressionThreshold = 10 #(default: 10px) – More than this horizontal displacement, and we will suppress scrolling.
    $.event.special.swipe.durationThreshold = 200 #(default: 1000ms) – More time than this, and it isn’t a swipe.
    $.event.special.swipe.horizontalDistanceThreshold = 150 #(default: 30px) – Swipe horizontal displacement must be more than this.
    $.event.special.swipe.verticalDistanceThreshold = 150 #(default: 75px) – Swipe vertical displacement must be less than this.

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