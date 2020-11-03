import { makeStyles } from '@material-ui/core/styles';

const drawerWidth = 240;

const useStyles = makeStyles((theme) => ({
    root: {
        display: 'flex'
    },
    cinemaRoot: {
        background: '#181E23'
    },
    paprykLogo: {
        width: "30%",
        height: "30%"
    },
    paprykLogoButton: {
        display: "block"
    },
    appBar: {
        zIndex: theme.zIndex.drawer + 1,
        color: 'white',
        background: '#181E23'
    },
    toolBar: {
        background: '#181E23'
    },
    menuBar: {
        float: 'left'
    },
    spanRedColor: {
        color: '#d328b4'
    },
    drawer: {
        width: drawerWidth,
        flexShrink: 0,
    },
    drawerPaper: {
        width: drawerWidth,
        background: '#181E23',
        height: "100%"
    },
    drawerContainer: {
        overflow: 'auto',
        color: 'white'
    },
    content: {
        flexGrow: 1,
        padding: theme.spacing(5),
        marginLeft: drawerWidth,
        width: '100%',
    },
    menuButton: {
        marginRight: theme.spacing(5),
    },
    title: {
        flexGrow: 1,
    },
    drawerIcon: {
        color: "white"
    },
    paper: {
        marginTop: theme.spacing(5),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(3),
    },
    loginRoot: {
        height: '100vh',
    },
    loginImage: {
        backgroundImage: "url('./drawer10.jpg')",
        backgroundRepeat: 'no-repeat',
        backgroundColor:
            theme.palette.type === 'light' ? theme.palette.grey[50] : theme.palette.grey[900],
        backgroundSize: 'cover',
        backgroundPosition: 'center',
    },
    loginPaper: {
        margin: theme.spacing(8, 4),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    loginAvatar: {
        margin: theme.spacing(1),
        backgroundColor: theme.palette.secondary.main,
    },
    loginForm: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(1),
    },
    submit: {
        margin: theme.spacing(3, 0, 2),
    },
    table: {
        minWidth: 650,
    },
    button: {
        margin: theme.spacing(1)
    },
    formControl: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(1),
    },
    selectEmpty: {
        marginTop: theme.spacing(2),
    },
    textField: {
        margin: theme.spacing(3),
        width: 200,
    },
    movieList: {
        marginTop: theme.spacing(5),
        display: 'flex',
        flexWrap: 'wrap',
        justifyContent: 'space-around',
        overflow: 'hidden',
        backgroundColor: theme.palette.background.paper,
    },
    gridList: {
        flexWrap: 'nowrap',
        transform: 'translateZ(0)',
    },
    movieTitle: {
        color: '#F81A36',
        fontWeight: "bold"
},
    titleBar: {
        background: '#d328b4'
    },
    icon: {
        color: 'rgba(255, 255, 255, 0.54)',
    },
    movieCarousel: {
        marginTop: theme.spacing(10)
    },
    petlaImage: {
        height: 600,
        backgroundImage: "url('./petla.jpg')",
        backgroundRepeat: 'no-repeat',
        backgroundSize: 'cover',
        backgroundPosition: 'center',
    },
    badboyImage: {
        height: 600,
        backgroundImage: "url('./badboy.jpg')",
        backgroundRepeat: 'no-repeat',
        backgroundSize: 'cover',
        backgroundPosition: 'top',
    },
    movieCarouselHeader: {
        fontSize: 60,
        position: "absolute",
        bottom: 70,
        right: 200,
        color: '#d328b4'
    },
    paprykRoot: {
        maxWidth: 345,
    },
    media: {
        height: 140,
    },
    whoIsPapryk: {
        width: "50%",
        height: "50%",
       position: "center"
    },
    whatsOn: {
        marginTop: theme.spacing(13),
        color: "white"
    },
    whatsOnTabs: {
            backgroundColor: theme.palette.background.paper,
    },
    movieCardContent: {
        width: 400,
        margin: theme.spacing(3),
    },
    checkoutAppBar: {
        position: 'relative',
    },
    checkoutLayout: {
        width: 'auto',
        height: 1000,
        marginLeft: theme.spacing(2),
        marginRight: theme.spacing(2),
        [theme.breakpoints.up(600 + theme.spacing(2) * 2)]: {
            width: 1000,
            marginLeft: 'auto',
            marginRight: 'auto',
        },
    },
    checkoutPaper: {
        marginTop: theme.spacing(3),
        marginBottom: theme.spacing(3),
        padding: theme.spacing(2),
        height: 800,
        [theme.breakpoints.up(600 + theme.spacing(3) * 2)]: {
            marginTop: theme.spacing(6),
            marginBottom: theme.spacing(6),
            padding: theme.spacing(3),
        },
    },
    checkoutStepper: {
        padding: theme.spacing(3, 0, 5),
    },
    checkoutButtons: {
        display: 'flex',
        justifyContent: 'flex-end',
    },
    checkoutButton: {
        marginTop: theme.spacing(3),
        marginLeft: theme.spacing(1),
    },
    cinemaHallRow: {
        float: "left"
    },
    cinemaHallSeatReserved: {
        width: 40,
        height: 40,
        background: "red",
        margin: 3
    },
    cinemaHallSeatFree: {
        width: 40,
        height: 40,
        background: "green",
        margin: 3
    },
    cinemaHall: {
        float: "left"

    }
}));

export default useStyles;