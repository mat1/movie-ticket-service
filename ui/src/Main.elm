module Main exposing (..)

import Html exposing (..)
import Navigation
import Router
import Overview
import MovieDetail


-- PROGRAM


main : Program Never Model Msg
main =
    Navigation.program OnLocationChange
        { init = init
        , view = view
        , update = update
        , subscriptions = subscriptions
        }


init : Navigation.Location -> ( Model, Cmd Msg )
init location =
    let
        currentRoute =
            Router.parseLocation location
    in
        getPage currentRoute


subscriptions : Model -> Sub Msg
subscriptions model =
    Sub.none



-- MODELS


type Page
    = Overview Overview.Model
    | MovieDetail MovieDetail.Model


type Msg
    = OnLocationChange Navigation.Location
    | OverviewMsg Overview.Msg
    | MovieDetailMsg MovieDetail.Msg


type alias Model =
    { page : Page
    }



-- UPDATE


update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
    let
        toPage toModel toMsg subUpdate subMsg subModel =
            let
                ( newModel, newCmd ) =
                    subUpdate subMsg subModel
            in
                ( { model | page = toModel newModel }, Cmd.map toMsg newCmd )
    in
        case ( msg, model.page ) of
            ( OnLocationChange location, _ ) ->
                let
                    newRoute =
                        Router.parseLocation location
                in
                    getPage newRoute

            ( OverviewMsg subMsg, Overview subModel ) ->
                toPage Overview OverviewMsg Overview.update subMsg subModel

            ( MovieDetailMsg subMsg, MovieDetail subModel ) ->
                toPage MovieDetail MovieDetailMsg MovieDetail.update subMsg subModel

            _ ->
                ( Debug.log "No match !!" model, Cmd.none )


getPage : Router.Route -> ( Model, Cmd Msg )
getPage route =
    let
        toPage toModel toMsg initPage =
            let
                ( newModel, newCmd ) =
                    initPage
            in
                ( Model (toModel newModel), Cmd.map toMsg newCmd )
    in
        case route of
            Router.Overview ->
                toPage Overview OverviewMsg Overview.init

            Router.MovieDetail id ->
                toPage MovieDetail MovieDetailMsg (MovieDetail.init id)



-- VIEWS


view : Model -> Html Msg
view model =
    case model.page of
        Overview subModel ->
            Overview.view subModel |> Html.map OverviewMsg

        MovieDetail subModel ->
            MovieDetail.view subModel |> Html.map MovieDetailMsg
