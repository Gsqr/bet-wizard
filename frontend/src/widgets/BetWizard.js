import * as React from 'react';
import Grid from '@material-ui/core/Grid/Grid';
import GenerateCouponWidget from './GenerateCouponWidget';
import CouponResultWidget from './CouponResultWidget';

class BetWizard extends React.Component {

    constructor(props, context) {
        super(props, context);

        this.state = {};
    }

    onGenerate = (request) => {
        console.log('Request is: ' + JSON.stringify(request));

        fetch('/bet-wizard/api/v2/generateBetCoupon', {
            method: 'post',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(request)
        }).then((response) => {
            if (response.ok) {
                console.log('Received response: ' + JSON.stringify(response));
                return response.json();
            } else {
                alert('Unable to generate coupon!');
            }
        }).then((data) => {
            this.setState({
                coupon: data
            });
        });
    };

    render() {
        return (
            <div>
                <Grid container spacing={8}>
                    <Grid item xs={4}>
                        <GenerateCouponWidget onGenerate={this.onGenerate}/>
                    </Grid>
                    <Grid item xs={8}>
                        {this.state.coupon && <CouponResultWidget coupon={this.state.coupon}/>}
                    </Grid>
                </Grid>
            </div>
        );
    }
}

export default BetWizard;