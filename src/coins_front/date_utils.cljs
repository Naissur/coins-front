(ns coins-front.date-utils
  (:require [coins-front.math-utils :as m]))

(defn now []
  (.now js/Date))

