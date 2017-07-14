(ns geddit.handlers.dongles
  (:require [ring.util.response :refer [response]]))

(defn json-response [json-str]
  (assoc-in
    (response json-str)
    [:headers "Content-Type"] "application/json"))

(defn list-dongles [params]
  (json-response (str "{\"response\": \"dongles!\",
                        \"params\": \"" (pr-str params) "\"}")))
