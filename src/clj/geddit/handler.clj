(ns geddit.handler
  (:require [compojure.core :refer [GET POST defroutes]]
            [compojure.route :refer [resources]]
            [ring.util.response :refer [response resource-response]]
            [ring.middleware.reload :refer [wrap-reload]]))

(defn json-response [json-str]
  (assoc-in
    (response json-str)
    [:headers "Content-Type"] "application/json"))

(defroutes routes
           (GET "/" [] (resource-response "index.html" {:root "public"}))
           (GET "/news" [] (response "yo what's up"))
           (POST "/dongles" [params] (json-response (str "{\"response\": \"dongles!\",
                                                           \"params\": \"" (pr-str params) "\"}")))
           (resources "/"))

(def dev-handler (-> #'routes wrap-reload))

(def handler routes)
