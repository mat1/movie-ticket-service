module Main exposing (..)

import Html exposing (..)
import Html.Events exposing (onClick, on, onInput)
import Http
import Html.Attributes as Attr exposing (id, class, classList, src, name, type_, title, href, rel, attribute, placeholder)
import Json.Decode exposing (string, int, list, Decoder, at)
import Json.Decode.Pipeline exposing (decode, required, optional)


main : Program Never Model Msg
main =
    Html.program
        { init = init
        , view = viewOrError
        , update = update
        , subscriptions = subscriptions
        }



-- MODEL


type alias Model =
    { movies : List Movie
    , loadedMovies : List Movie
    , loadingError : Maybe String
    , selectedMovie : Maybe MovieDetail
    }


type alias Movie =
    { id : Int
    , poster : String
    , title : String
    }


type alias MovieDetail =
    { id : Int
    , title : String
    , plot : String
    , genre : String
    }


init : ( Model, Cmd Msg )
init =
    ( { movies = [], loadedMovies = [], loadingError = Nothing, selectedMovie = Nothing }, initialCmd )


initialCmd : Cmd Msg
initialCmd =
    list movieDecoder
        |> Http.get "http://localhost:8080/api/v1/movies"
        |> Http.send LoadMovies


movieDecoder : Decoder Movie
movieDecoder =
    decode Movie
        |> required "id" int
        |> required "poster" string
        |> required "title" string


movieDetailDecoder : Decoder MovieDetail
movieDetailDecoder =
    decode MovieDetail
        |> required "id" int
        |> required "title" string
        |> required "plot" string
        |> required "genre" string


loadMovie : Int -> Cmd Msg
loadMovie id =
    movieDetailDecoder
        |> Http.get ("http://localhost:8080/api/v1/movies/" ++ (toString id))
        |> Http.send LoadMovie



-- UPDATE


type Msg
    = ShowDetails
    | LoadMovies (Result Http.Error (List Movie))
    | FilterMovies String
    | SelectMovie Int
    | LoadMovie (Result Http.Error MovieDetail)


update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
    case msg of
        ShowDetails ->
            ( model, Cmd.none )

        LoadMovies (Ok movies) ->
            ( { model | loadedMovies = movies, movies = movies }, Cmd.none )

        LoadMovies (Err err) ->
            ( { model | loadingError = Just (toString err) }, Cmd.none )

        FilterMovies title ->
            ( filterMovies title model, Cmd.none )

        SelectMovie id ->
            ( model, (loadMovie id) )

        LoadMovie (Ok movieDetail) ->
            ( { model | selectedMovie = Just movieDetail }, Cmd.none )

        LoadMovie (Err err) ->
            ( model, Cmd.none )


filterMovies : String -> Model -> Model
filterMovies title model =
    { model | movies = List.filter (\m -> String.contains (String.toLower title) (String.toLower m.title)) model.loadedMovies }



-- VIEW


viewOrError : Model -> Html Msg
viewOrError model =
    case model.loadingError of
        Just error ->
            text error

        Nothing ->
            view model


view : Model -> Html Msg
view model =
    div [ class "container-fluid" ]
        [ navbar
        , div [ class "row" ] (List.map viewMovie model.movies)
        ]


viewMovie : Movie -> Html Msg
viewMovie movie =
    div [ class "col" ]
        [ img [ class "poster", src movie.poster, onClick (SelectMovie movie.id) ] [] ]


navbar : Html Msg
navbar =
    nav [ class "navbar navbar-dark bg-dark" ]
        [ a [ class "navbar-brand", href "#" ] [ text "Movie Ticket Service" ]
        , form [ class "form-inline" ]
            [ input [ attribute "aria-label" "Search", class "form-control", placeholder "Search", type_ "text", onInput FilterMovies ] []
            ]
        ]



-- SUBSCRIPTIONS


subscriptions : Model -> Sub Msg
subscriptions model =
    Sub.none
