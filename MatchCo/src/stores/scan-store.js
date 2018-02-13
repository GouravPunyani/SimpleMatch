// @flow
import { observable, toJS, action } from 'mobx'
import { Scan } from '../entities/scan'
import { getLatestScan } from '../lib/scan'

export class ScanStore {
  @observable.ref scan: Scan = null

  constructor (state?: *) {
    if (state) {
      this.scan = state && state.scan
    }
  }

  /** retrieves the latest available scan */
  @action async getLatestScan () {
    const scan = await getLatestScan()
    this.setScan(scan)
  }

  /** sets the current scan */
  @action setScan (newScan: Scan) {
    this.scan = newScan
  }

  serialize (): Object {
    return toJS({
      scan: this.scan
    })
  }
}
