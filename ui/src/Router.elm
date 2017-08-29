module Router exposing (Route(..), parseLocation)

import Navigation
import UrlParser exposing ((</>))


type Route
    = Overview
    | MovieDetail Int



-- ROUTING


matchers : UrlParser.Parser (Route -> a) a
matchers =
    UrlParser.oneOf
        [ UrlParser.map Overview UrlParser.top
        , UrlParser.map MovieDetail (UrlParser.s "movie" </> UrlParser.int)
        ]


parseLocation : Navigation.Location -> Route
parseLocation location =
    case (UrlParser.parseHash matchers location) of
        Just route ->
            route

        Nothing ->
            Overview
