(ns ep41.tea-table
  (:require [reagent.core :as r]
            [cljsjs.react-ultimate-pagination]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; pagination

(defn page [{:keys [value isActive onClick isDisabled]}]
  [:button {:style (if isActive {:font-weight "bold"}), :on-click onClick, :disabled isDisabled} value])

(defn ellipsis [{:keys [onClick isDisabled]}]
  [:button {:on-click onClick, :disabled isDisabled} "..."])

(defn first-page-link [{:keys [onClick isDisabled]}]
  [:button {:on-click onClick, :disabled isDisabled} "<<"])

(defn last-page-link [{:keys [onClick isDisabled]}]
  [:button {:on-click onClick, :disabled isDisabled} ">>"])

(defn next-page-link [{:keys [onClick isDisabled]}]
  [:button {:on-click onClick, :disabled isDisabled} ">"])

(defn previous-page-link [{:keys [onClick isDisabled]}]
  [:button {:on-click onClick, :disabled isDisabled} "<"])

(defn wrapper [{:keys [children]}]
  [:div {:style {:padding "1em 0"}}
   children])

(def pagination-config
  #js {:itemTypeToComponent
       #js {:PAGE               (r/reactify-component page)
            :ELLIPSIS           (r/reactify-component ellipsis)
            :FIRST_PAGE_LINK    (r/reactify-component first-page-link)
            :LAST_PAGE_LINK     (r/reactify-component last-page-link)
            :NEXT_PAGE_LINK     (r/reactify-component next-page-link)
            :PREVIOUS_PAGE_LINK (r/reactify-component previous-page-link)}
       :WrapperComponent (r/reactify-component wrapper)})

(def pagination
  (r/adapt-react-class
   (.createUltimatePagination js/ReactUltimatePagination pagination-config)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; state

(def state (r/atom {:page 1
                    :page-size 10
                    :data []}))

(defn total-pages []
  (let [{:keys [page-size data]} @state]
    (max 1 (Math/ceil (/ (count data) page-size)))))

(defn current-page []
  (:page @state))

(defn set-page! [page]
  (swap! state assoc :page page))

(defn fetch-data []
  (-> (js/fetch "teas.json")
      (.then #(.json %))
      (.then (fn [json]
               (swap! state assoc :data json)))))

(defn current-page-data []
  (let [{:keys [page page-size data]} @state]
    (->> data
         (drop (* (dec page) page-size))
         (take page-size))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Views

(defn table [rows]
  `[:table
    [:thead
     [:tr ~@(for [x ["Title" "Name" "Type" "Origin"]]
              [:th x])]]
    [:tbody
     ~@(for [row rows]
         `[:tr ~@(for [cell row] [:td cell])])]])

(defn app []
  [:div
   [:h1 "List of Teas"]
   [table (current-page-data)]
   [pagination {:currentPage (current-page)
                :totalPages (total-pages)
                :onChange set-page!}]])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Top level

(r/render [app] (js/document.getElementById "app"))
(fetch-data)

















