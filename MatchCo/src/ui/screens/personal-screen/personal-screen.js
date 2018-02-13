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
export class PersonalScreen extends Component {
  static propTypes: {
    navigation: any
  };

  render () {
    const { userStore, navigation, translate } = this.props
    const navigate = (path, options = {}) => () => navigation.navigate(path, options)

    return (
      // TODO: The information is not being pulled from account
      <StaticLayout>
        <ButtonPanel>
          <View style={styles.wrapper}>
            <Text style={styles.title}>{translate('account.nameAndEmail')}</Text>
            <Button text={translate('account.update')} onPress={navigate('changeProfile')} analyticsName={('changeProfile')}
            textStyle={{color: '#dcb8bd', textDecorationLine: 'underline', fontSize: 12}}
            style={{minHeight: 20, backgroundColor: 'transparent', shadowOpacity: 0}} />
          </View>
          <View style={{width: width*.9, height: 1, backgroundColor: 'gainsboro'}}/>
          <View style={{paddingVertical: 7, paddingHorizontal: 5}}>
            <View style={{flexDirection: 'row'}}>
              <Text style={styles.text}>{'Matthieu'/* <-- Will need to be taken from account*/}</Text>
              <Text style={styles.text}>{'Rey'/* <-- Will need to be taken from account*/}</Text>
            </View>
            <Text style={styles.text}>{'matthieu@getmatch.co'/* <-- Will need to be taken from account*/}</Text>
          </View>
          <Text style={styles.title}>{translate('account.phone')}</Text>
          <View style={{width: width*.9, height: 1, backgroundColor: 'gainsboro'}}/>
          <View style={{flexDirection: 'row', paddingVertical: 7, paddingHorizontal: 5}}>
            <Text style={styles.text}>{translate('account.cell')}</Text>
            <Text style={styles.text}>{'123.456.7890'/* <-- Will need to be taken from account*/}</Text>
          </View>
          <View style={styles.wrapper}>
            <Text style={styles.title}>{translate('account.password')}</Text>
            <Button text={translate('account.change')} onPress={navigate('changePassword')}
            textStyle={{color: '#dcb8bd', textDecorationLine: 'underline', fontSize: 12}}
            style={{minHeight: 20, backgroundColor: 'transparent', shadowOpacity: 0}} />
          </View>
          <View style={{width: width*.9, height: 1, backgroundColor: 'gainsboro'}}/>
          <View style={{paddingVertical: 7, paddingHorizontal: 5}}>
            <Text style={styles.text}>••••••••</Text>
          </View>
        </ButtonPanel>
      </StaticLayout>
    )
  }
}

const styles = StyleSheet.create({
  wrapper: { flexDirection: 'row', justifyContent: 'space-between' },
  button: {minHeight: 20, backgroundColor: 'transparent', shadowOpacity: 0},
  buttonText: {color: 'dimgray', textDecorationLine: 'underline', fontSize: 12},
  title: { color: 'black', fontSize: 14, padding: 7 },
  text: { color: 'dimgray', fontSize: 12, paddingHorizontal: 2 }
})
