import { execFileSync } from 'node:child_process';
import { writeFileSync } from 'node:fs';

const version = process.env.RELEASE_VERSION;
const repository = process.env.GITHUB_REPOSITORY;
if (!/^\d+\.\d+\.\d+$/.test(version ?? '') ||
    !/^[\w-]+\/[\w.-]+$/.test(repository ?? '')) {
  throw new Error('RELEASE_VERSION and GITHUB_REPOSITORY must identify a stable release.');
}
const release = JSON.parse(execFileSync('gh', [
  'release', 'view', `v${version}`, '--repo', repository,
  '--json', 'publishedAt,body,assets,isDraft,isPrerelease',
], { encoding: 'utf8' }));
const name = `patches-${version}.mpp`;
const asset = release.assets.find(asset => asset.name === name);
if (release.isDraft || release.isPrerelease || !release.publishedAt || !asset || asset.size <= 0) {
  throw new Error('A published stable release with a nonempty .mpp asset is required.');
}
writeFileSync('patches-bundle.json', JSON.stringify({
  created_at: release.publishedAt.replace(/Z$/, ''),
  description: release.body,
  download_url: asset.url,
  signature_download_url: '',
  version,
}, null, 2) + '\n');
