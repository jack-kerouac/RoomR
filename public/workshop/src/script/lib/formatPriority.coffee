# Die **Prioritäts-Formatierungs-Funktion** ist ein Beispiel für ein AMD-Modul. Es besteht
# immer aus einem Aufruf der Funktion `define()`, die (optional) ein Array mit
# Abhängigkeiten sowie einen Callback übergeben bekommt. Der Inhalt dieses Callbacks ist
# der Modul-Code und das zurückgegebene Objekt ist die API - in diesem Fall nur eine
# Funktion, die Prioritäts-Zahlencodes in entsprechende Strings verwandelt.

define ->
  'use strict'
  return (priority) ->
    return switch priority
      when 0 then 'Niedrig'
      when 1 then 'Normal'
      when 2 then 'Hoch'