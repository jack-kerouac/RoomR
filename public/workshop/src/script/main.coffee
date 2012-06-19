# Fast die gesamte Beispielapp steckt in dieser Datei. Die aus den Kommentaren generierte
# Dokumentation liegt im `docs`-Verzeichnis. Die Kommentare können mit
# [Markdown](http://daringfireball.net/projects/markdown/) formatiert werden.


# Abhängigkeiten
# --------------

# Die `require()`-Funktion erwartet zwei Argumente: erstens ein Array mit den
# Abhängigkeiten für dieses Script und zweitens die Callback-Funktion, die ausgeführt
# wird, sobald alle Abhängigkeiten geladen wurden. Wenn die Abhängigkeiten selbst
# AMD-Module sind und Objekte zurückgeben, werden diese an den Callback als Argumente
# übergeben. Das ist nicht immer der Fall -- das `backbone`-Modul hat z.B. jQuery und
# [Underscrore.js](http://underscorejs.org/) Abhängigkeiten und lädt diese auch. Da
# je doch diese beiden Libraries  ihre APIs (`$` und `_`) als globale Objekte
# bereitstellen, können wir beides in unserer Callback-Funkion verwenden, ohne sie dort
# im Callback explizit aufzuführen.
require ['backbone', 'Navigation', 'models/UserCollection', 'AppRouter', 'base/RoomrSection', 'widgets/SearchWidget', 'widgets/SearchResultWidget', 'widgets/LoginStatusFinder'],
(Backbone, Navigation, UserCollection, AppRouter, RoomrSection, SearchWidget, SearchResultWidget, LoginStatusFinder) ->
  'use strict'

  finder = new LoginStatusFinder()

  testSection = new RoomrSection {
      name: 'main'
      title: 'test title'
    }
  searchWidget = new SearchWidget()
  testSection.addWidget(searchWidget)

  searchResultWidget = new SearchResultWidget()
  testSection.addWidget(searchResultWidget)

  searchWidget.subscribe 'searchResultsChanged', (params...) ->
    searchResultWidget.searchResultsChanged.apply(searchResultWidget, params)



  testSection.render()

  users = new UserCollection()

  nav = new Navigation()

  # Router anwerfen
  app = new AppRouter(nav)

  nav.app = app
  nav.users = users


  # App starten
  # -----------

  # Die URL auf Änderungen überwachen
  Backbone.history.start()

  finder.findOutState()
