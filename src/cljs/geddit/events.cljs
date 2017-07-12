(ns geddit.events
  (:require [re-frame.core :as re-frame]
            [geddit.db :as db]))

(defonce last-temp-id (atom 0))

(re-frame/reg-cofx
  :temp-id ;; same name
  (fn [cofx _]
    (assoc cofx :temp-id (swap! last-temp-id inc))))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/reg-event-db
 :set-active-panel
 (fn [db [_ active-panel]]
   (assoc db :active-panel active-panel)))

(re-frame/reg-event-fx
  :create-dongle
  [(re-frame/inject-cofx :temp-id)]
  (fn [cofx [_ name]]
    {;:http-xhrio {:uri (str "http://localhost:3449/dongles")
     ;             :method :post
     ;             :timeout 10000
     ;             ;:response-format (ajax/json-response-format {:keywords? true})
     ;             ;:on-success [:added-cart (:temp-id cofx)]       ;; start using temp-id
     ;             ;:on-failure [:notified-error (:temp-id cofx)]
     ;             }
     :db (update-in (:db cofx) [:dongles] conj {:name name :id (:temp-id cofx)})}))
