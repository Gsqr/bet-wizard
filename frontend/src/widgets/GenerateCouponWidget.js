import * as React from 'react';
import {withStyles} from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper/Paper';
import Typography from '@material-ui/core/Typography/Typography';
import TextField from '@material-ui/core/TextField/TextField';
import Button from '@material-ui/core/Button/Button';
import FormControl from '@material-ui/core/FormControl/FormControl';
import InputLabel from '@material-ui/core/InputLabel/InputLabel';
import Select from '@material-ui/core/Select/Select';
import Input from '@material-ui/core/Input/Input';
import MenuItem from '@material-ui/core/MenuItem/MenuItem';
import FormLabel from '@material-ui/core/FormLabel/FormLabel';
import RadioGroup from '@material-ui/core/RadioGroup/RadioGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel/FormControlLabel';
import Radio from '@material-ui/core/Radio/Radio';

const styles = theme => ({
    container: {
        display: 'flex',
        flexWrap: 'wrap',
        flexDirection: 'column'
    },
    formControl: {
        margin: theme.spacing.unit,
        width: 300
    },
    textField: {
        marginLeft: theme.spacing.unit,
        marginRight: theme.spacing.unit,
        width: 300
    },
    dense: {
        marginTop: 19
    },
    menu: {
        width: 200
    },
    group: {
        margin: `${theme.spacing.unit}px 0`,
    },
    button: {
        margin: theme.spacing.unit,
        width: 200
    }
});

class GenerateCouponWidget extends React.Component {

    eventGroups = ['Football', 'Football - England', 'Football - France', 'Football - England - Premier League', 'Tennis', 'Tennis - US Open'];

    timeOptions = [
        {
            type: 'nextHour',
            text: 'Next hour',
            hours: 1
        },
        {
            type: 'next3Hours',
            text: 'Next 3 hours',
            hours: 3
        },
        {
            type: 'next12Hours',
            text: 'Next 12 hours',
            hours: 12
        },
        {
            type: 'nextDay',
            text: 'Next day',
            hours: 24
        },
        {
            type: 'nextWeek',
            text: 'Next week',
            hours: 24 * 7
        },
        {
            type: 'nextMonth',
            text: 'Next month',
            hours: 24 * 31
        }
    ];

    constructor(props, context) {
        super(props, context);

        this.state = {
            // stake: 10,
            // payout: 100,
            eventPath: 'Football',
            time: this.timeOptions[3],
            // slipSize: 5,
            maxOdds: '5.00',
            couponType: 'single'
        }
    }

    handleChange = name => event => {
        this.setState({
            [name]: event.target.value
        });
    };

    handleSelectChange = event => {
        this.setState({[event.target.name]: event.target.value});
    };

    createRequest = () => {
        const toDate = new Date();
        toDate.setHours(toDate.getHours() + this.state.time.hours);

        return {
            stake: this.state.stake,
            payout: this.state.payout,
            eventPath: [this.state.eventPath],
            maxOdds: Number.parseFloat(this.state.maxOdds) * 100,
            slipSize: this.state.slipSize,
            fromDate: new Date(),
            toDate: toDate,
            combination: this.state.couponType === 'combination'
        }
    };

    validate = (request) => {
        if(request.slipSize && request.maxOdds){
            const maxPayout = request.maxOdds / 100 * request.slipSize * request.stake;
            if(maxPayout < request.payout){
                alert('With inputed max odds and slip size you will be able to win maximum ' + maxPayout + ' euro! ' +
                    'Please update parameters!');
                return false;
            }
        }
        return true;
    };

    onGenerate = () => {
        const request = this.createRequest();
        if (this.validate(request)) {
            this.props.onGenerate(request);
        }
    };

    render() {
        const {classes} = this.props;

        return (
            <Paper>
                <Typography variant="h7" gutterBottom>
                    Generate new coupon given following parameters:
                </Typography>
                <form className={classes.container} noValidate autoComplete="off">
                    <TextField
                        id="stake"
                        label="I want to bet (euro)"
                        type="number"
                        className={classes.textField}
                        value={this.state.stake}
                        onChange={this.handleChange('stake')}
                        margin="normal"
                    />
                    <TextField
                        id="payout"
                        label="I want to win at least (euro)"
                        type="number"
                        className={classes.textField}
                        value={this.state.payout}
                        onChange={this.handleChange('payout')}
                        margin="normal"
                    />
                    <FormControl className={classes.formControl}>
                        <InputLabel htmlFor="event-path-helper">I want events for the following group</InputLabel>
                        <Select
                            value={this.state.eventPath}
                            onChange={this.handleSelectChange}
                            input={<Input name="eventPath" id="event-path-helper"/>}>
                            {
                                this.eventGroups.map(group => {
                                    return <MenuItem key={group} value={group}>{group}</MenuItem>
                                })
                            }
                        </Select>
                    </FormControl>
                    <FormControl className={classes.formControl}>
                        <InputLabel htmlFor="time-helper">Events during</InputLabel>
                        <Select
                            value={this.state.time}
                            onChange={this.handleSelectChange}
                            input={<Input name="time" id="time-helper"/>}>
                            {
                                this.timeOptions.map(to => {
                                    return <MenuItem key={to.type} value={to}>{to.text}</MenuItem>
                                })
                            }
                        </Select>
                    </FormControl>
                    <TextField
                        id="slipSize"
                        label="Slip size"
                        type="number"
                        className={classes.textField}
                        value={this.state.slipSize}
                        onChange={this.handleChange('slipSize')}
                        margin="normal"
                    />
                    <TextField
                        id="maxOdds"
                        label="Outcomes having maximum odds"
                        type="text"
                        className={classes.textField}
                        value={this.state.maxOdds}
                        onChange={this.handleChange('maxOdds')}
                        margin="normal"
                    />
                    <FormControl component="fieldset" className={classes.formControl}>
                        <FormLabel component="legend">Coupon type</FormLabel>
                        <RadioGroup
                            aria-label="couponType"
                            name="couponType"
                            className={classes.group}
                            value={this.state.couponType}
                            onChange={this.handleSelectChange}
                            style={{flexDirection: 'row'}}
                        >
                            <FormControlLabel value="single" control={<Radio />} label="Single" />
                            <FormControlLabel value="combination" control={<Radio />} label="Combination" />
                        </RadioGroup>
                    </FormControl>
                    <Button
                        variant="contained" color="primary" className={classes.button}
                        onClick={this.onGenerate}>
                        Generate coupon
                    </Button>
                </form>
            </Paper>
        );
    }
}

export default withStyles(styles)(GenerateCouponWidget);