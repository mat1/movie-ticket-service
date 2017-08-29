module Overview exposing (..)

import Html exposing (..)
import Html.Events exposing (onClick, on, onInput, onMouseOver)
import Html.Attributes as Attr exposing (id, class, classList, src, name, type_, title, href, rel, attribute, placeholder)
import MovieApi exposing (..)
import Http


-- MODEL


type alias Model =
    { movies : List Movie
    , loadedMovies : List Movie
    , loadingError : Maybe String
    , selectedMovie : Maybe MovieDetail
    }


init : ( Model, Cmd Msg )
init =
    ( initialModel, (MovieApi.loadMovies LoadMovies) )


initialModel : Model
initialModel =
    { movies = [], loadedMovies = [], loadingError = Nothing, selectedMovie = Nothing }



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
            ( model, (MovieApi.loadMovie id LoadMovie) )

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
        , div [ class "row" ] (List.map (viewMovie model.selectedMovie) model.movies)
        ]


viewMovie : Maybe MovieDetail -> Movie -> Html Msg
viewMovie movieDetail movie =
    div [ class "col" ]
        [ div [ class "movie" ]
            [ img [ class "poster", src movie.poster, onMouseOver (SelectMovie movie.id) ] []
            , viewMovieDetail movieDetail movie.id
            ]
        ]


emptyNode : Html Msg
emptyNode =
    text ""


viewMovieDetail : Maybe MovieDetail -> Int -> Html Msg
viewMovieDetail movieDetail selectedId =
    case movieDetail of
        Nothing ->
            emptyNode

        Just detail ->
            if detail.id == selectedId then
                div [ class "movie-details" ]
                    [ h4 [] [ text detail.title ]
                    , span [] [ text detail.plot ]
                    ]
            else
                emptyNode


navbar : Html Msg
navbar =
    nav [ class "navbar navbar-dark bg-dark" ]
        [ a [ class "navbar-brand", href "#", id "logo" ] [ text "Movie Ticket Service" ]
        , form [ class "form-inline" ]
            [ input [ attribute "aria-label" "Search", class "form-control", placeholder "Search", type_ "text", onInput FilterMovies ] []
            ]
        ]



-- SUBSCRIPTIONS


subscriptions : Model -> Sub Msg
subscriptions model =
    Sub.none
