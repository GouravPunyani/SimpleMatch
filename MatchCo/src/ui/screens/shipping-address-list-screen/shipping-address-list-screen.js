// @flow

import React, { Component } from 'react'
import { View, Text, StyleSheet, Dimensions } from 'react-native'
import { inject, observer } from 'mobx-react'
import { Button, ButtonPanel, StaticLayout } from '../..'
import type { UserStore } from '../../../stores/user-store'
import { translator } from '../../../services/translate/translator'
const { width, height } = Dimensions.get('window')

@translator
@inject('userStore')
@observer
export class ShippingAddressListScreen extends Component {
  static propTypes: {
    navigation: any
  };

  render () {
    const { userStore, navigation, translate } = this.props
    const navigate = (path, options = {}) => () => navigation.navigate(path, options)

    return (
      // TODO: Allow for multiple shipping addresses
      <StaticLayout>
        <ButtonPanel>
          <Text style={styles.title}>{translate('account.shippingAddress')}</Text>
          <View style={{width: width*.9, height: 1, backgroundColor: '#dcb8bd'}}/>
          <View style={styles.wrapper}>
            <View>
              <View style={{flexDirection:'row'}}>
                <Text style={styles.text}>{'Matthieu'/* <-- Will need to be taken from account*/}</Text>
                <Text style={styles.text}>{'Rey'/* <-- Will need to be taken from account*/}</Text>
              </View>
              <Text style={styles.text}>{'1421 5th Street'/* <-- Will need to be taken from account*/}</Text>
              <View style={{flexDirection:'row'}}>
                <Text style={styles.text}>{'Santa Monica'/* <-- Will need to be taken from account*/}</Text>
                <Text style={styles.text}>{'CA'/* <-- Will need to be taken from account*/}</Text>
                <Text style={styles.text}>{'90401'/* <-- Will need to be taken from account*/}</Text>
              </View>
              <Text style={styles.text}>{'123.456.7890'/* <-- Will need to be taken from account*/}</Text>
            </View>
            <Button text={translate('account.update')} onPress={navigate('shippingAddress')}
            textStyle={{color: '#dcb8bd', textDecorationLine: 'underline', fontSize: 12}}
            style={{minHeight: 20, backgroundColor: 'transparent', shadowOpacity: 0}} />
          </View>
        </ButtonPanel>
      </StaticLayout>
    )
  }
}

const styles = StyleSheet.create({
  wrapper: { flexDirection: 'row', justifyContent: 'space-between', paddingVertical: 7, paddingHorizontal: 5 },
  button: {minHeight: 20, backgroundColor: 'transparent', shadowOpacity: 0},
  buttonText: {color: 'dimgray', textDecorationLine: 'underline', fontSize: 12},
  title: { color: 'black', fontSize: 14, padding: 7 },
  text: { color: 'dimgray', fontSize: 12, paddingHorizontal: 2 }
})
