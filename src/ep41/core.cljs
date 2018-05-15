(ns ep41.core
  (:require [reagent.core :as r]))



(def state (r/atom {:page 1
                    :page-size 10
                    :rows []}))

(defn table [rows]
  [:table
   [:thead
    (into [:tr]
          (for [x ["Title" "Name" "Type" "Origin" "Description"]]
            [:th x]))]
   (into [:tbody]
         (for [row rows]
           (into [:tr]
                 (for [cell row]
                   [:td cell]))))])

(defn app []
  (let [{:keys [page page-size rows]} @state]
    [table (->> rows
                (drop (* (dec page) page-size))
                (take page-size))]))

(r/render [app] (js/document.getElementById "app"))

(-> (js/fetch "teas.json")
    (.then #(.json %))
    (.then (fn [json]
             (swap! state assoc :rows json))))
