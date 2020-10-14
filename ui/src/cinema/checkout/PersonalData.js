import React from "react";
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import TextField from '@material-ui/core/TextField';
import useLocalStorage from "../../localstorage/useLocalStorage";
import * as HttpService from "../../http/HttpService";
import CheckoutButton from "../../common/CheckoutButton";

export default function PersonalData(props) {
    const chosenMovie = useLocalStorage('chosenMovie')[0];
    const [personalDataId, setPersonalDataId] = useLocalStorage('personalDataId', null);
    const [lastName, setLastName] = React.useState('');
    const [firstName, setFirstName] = React.useState('');
    const [phone, setPhone] = React.useState('');
    const [email, setEmail] = React.useState('');

    const addPersonalData = () => {
        const personalData = {
            firstName: firstName,
            lastName: lastName,
            phoneNumber: phone,
            email: email,
        }
        HttpService.postJson('personaldata/add', personalData)
            .then(data => {
                    data.text().then(personalDataId => {
                        setPersonalDataId(personalDataId)
                        props.handleNext()
                    })
                }
            )
    }

    return (
        <>
            <Typography variant="h6" gutterBottom>{chosenMovie.title}</Typography>
            <Grid container spacing={3}>
                <Grid item xs={12} sm={6}>
                    <TextField required
                               id="firstName"
                               name="firstName"
                               label="First name"
                               value={firstName}
                               onChange={event => setFirstName(event.target.value)}
                               fullWidth/>
                </Grid>
                <Grid item xs={12} sm={6}>
                    <TextField required
                               id="lastName"
                               name="lastName"
                               label="Last name"
                               value={lastName}
                               onChange={event => setLastName(event.target.value)}
                               fullWidth/>
                </Grid>
                <Grid item xs={12} sm={6}>
                    <TextField required
                               id="phone"
                               name="Phone number"
                               label="Phone number"
                               type="number"
                               value={phone}
                               onChange={event => setPhone(event.target.value)}
                               fullWidth/>
                </Grid>
                <Grid item xs={12} sm={6}>
                    <TextField required
                               id="email"
                               name="Email"
                               label="Email"
                               value={email}
                               onChange={event => setEmail(event.target.value)}
                               fullWidth/>
                </Grid>
            </Grid>
            <CheckoutButton function={addPersonalData} name={"Next"}/>
        </>
    )
}