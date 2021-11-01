import os
from os import listdir
from os.path import isfile, join
from shutil import copyfile

onlyfiles = [f for f in listdir('.') if isfile(join('.', f))]

for f in onlyfiles:
    if f.startswith('flow'):
        new_f = f.replace('flow', 'v4').replace('-', '_')
        copyfile(f, new_f)