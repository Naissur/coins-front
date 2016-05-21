
tmux new-session -d -s coins-front

## Whiteboard session

# Org-mode window
tmux select-window -t coins-front:0
tmux rename-window 'org'

# Build window
tmux new-window -n build -t coins-front
tmux send-keys 'clear' 'Enter'
tmux send-keys 'rlwrap lein figwheel'
tmux send-keys 'Enter'

# Source window
tmux new-window -n src -t coins-front
tmux send-keys 'cd src/coins_front' 'Enter'
tmux send-keys 'clear' 'Enter'
tmux send-keys 'vim'
tmux send-keys 'Enter'

# Directory window
tmux new-window -n dir -t coins-front
tmux send-keys 'clear' 'Enter'


# Docs window
tmux new-window -n doc -t coins-front
tmux send-keys 'clear' 'Enter'
tmux send-keys 'rlwrap lein repl'
tmux send-keys 'Enter'


tmux attach-session -t coins-front
