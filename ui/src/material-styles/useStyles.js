import { makeStyles } from '@material-ui/core/styles';

const drawerWidth = 240;
const backgroundProperties = {
    height: 600,
    backgroundRepeat: 'no-repeat',
    backgroundSize: 'cover',
    backgroundPosition: 'center',
}

const useStyles = makeStyles((theme) => ({
    root: {
        display: 'flex'
    },
    cinemaRoot: {
        background: 'black'
    },
    appBar: {
        zIndex: theme.zIndex.drawer + 1,
        color: 'white',
        background: 'black'
    },
    toolBar: {
        background: 'black'
    },
    menuBar: {
        float: 'left'
    },
    spanRedColor: {
        color: '#ff3366'
    },
    drawer: {
        width: drawerWidth,
        flexShrink: 0,
    },
    drawerPaper: {
        width: drawerWidth,
        background: 'rgba(0, 0, 0, 0.87)',
        height: "100%"
    },
    drawerContainer: {
        overflow: 'auto',
        color: 'white'
    },
    content: {
        flexGrow: 1,
        padding: theme.spacing(3),
        marginLeft: drawerWidth,
        width: '100%',
    },
    menuButton: {
        marginRight: theme.spacing(2),
    },
    title: {
        flexGrow: 1,
    },
    drawerIcon: {
        color: "white"
    },
    paper: {
        marginTop: theme.spacing(3),
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
        color: '#ff3366',
        fontWeight: "bold"
},
    titleBar: {
        background: '#ff3366'
    },
    icon: {
        color: 'rgba(255, 255, 255, 0.54)',
    },
    movieCarousel: {
        marginTop: theme.spacing(5)
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
        color: '#ff3366'
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
    }
}));

export default useStyles;