// @flow

import React, { Component } from 'react'
import { View, Text, StyleSheet } from 'react-native'
import { Button, ButtonPanel } from '../..'
import { bind } from 'decko'

export class OrderReviewScreen extends Component {
  static propTypes: {
    navigation: any
  };

  @bind onPressShipping () {
    this.props.navigation.navigate('shippingAddress')
  }

  @bind onPressPayment () {
    this.props.navigation.navigate('paymentMethod')
  }

  @bind onPressPlaceOrder () {
    this.props.navigation.navigate('orderConfirmation')
  }

  render () {
    return (
      <View style={styles.wrapper}>
        <Text style={styles.text}>OrderReviewScreen</Text>
        <Text style={styles.target}>
          src/ui/screens/order-review-screen/order-review-screen.js
        </Text>
        <ButtonPanel>
          <Button text='Shipping Address' onPress={this.onPressShipping} />
          <Button text='Payment Method' onPress={this.onPressPayment} />
          <Button text='Place Order' onPress={this.onPressPlaceOrder} />
        </ButtonPanel>

      </View>
    )
  }
}

const styles = StyleSheet.create({
  wrapper: {
    flex: 1,
    backgroundColor: 'rebeccapurple',
    justifyContent: 'center',
    alignItems: 'center'
  },
  text: { color: 'white', fontSize: 30 },
  target: { color: 'white', fontSize: 12 }
})
